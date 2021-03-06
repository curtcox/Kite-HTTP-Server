package mite.reflect

import mite.ast.ReflectionNodeRenderer
import mite.core.Log
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import kotlin.reflect.*
import kotlin.reflect.jvm.*

data class Callable(val target:Any, val member: KCallable<*>) {

    // There must be a better way to determine if a callable can safely be called and yet here we are.
    fun couldBeCalled() =
        couldBeCalledWithRightValue() && valueTypeMatches(member.parameters[0].type)

    fun couldBeCalledWithRightValue() =
        try {
            member.returnType.toString()!="kotlin.Unit" &&
            member.parameters.size == 1 &&
            !member.name.startsWith("component") &&
            !isStatic()
        } catch (t: Throwable) {
            false // backoff if anything throws an exception
        }

    private fun isStatic() = if (member is KFunction) isStatic(member.javaMethod) else false

    private fun isStatic(method: Method?) = if (method==null) false else Modifier.isStatic(method.modifiers)

    private fun valueTypeMatches(t : KType) =
        declassify(t) == declassify(target::class) || declassify(t.javaType) == declassify(target::class)

    private fun declassify(c: Any) = c.toString().replace("class ","")

    fun call(): Any? =
        try {
            member.isAccessible = true
            member.call(target)
        } catch (t: Throwable) {
            Log.log(ReflectionNodeRenderer::class,target,t)
            t
        }
}