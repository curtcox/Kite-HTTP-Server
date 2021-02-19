package mite.reflect

import mite.ast.ReflectionRenderer
import mite.core.Log
import kotlin.reflect.*
import kotlin.reflect.jvm.*

data class Callable(val target:Any, val member: KCallable<*>) {

    // There must be a better way to determine if a callable can safely be called and yet here we are.
    fun couldBeCalled() =
        try {
            member.returnType.toString()!="kotlin.Unit" &&
            member.parameters.size == 1 &&
            valueTypeMatches(member.parameters[0].type) &&
            !member.name.startsWith("component")
        } catch (t: Throwable) {
            false // backoff if anything throws an exception
        }

    private fun valueTypeMatches(t : KType) =
        declassify(t) == declassify(target::class) || declassify(t.javaType) == declassify(target::class)

    private fun declassify(c: Any) = c.toString().replace("class ","")

    fun call(): Any? =
        try {
            member.isAccessible = true
            member.call(target)
        } catch (t: Throwable) {
            Log.log(ReflectionRenderer::class,target,t)
            t
        }

}