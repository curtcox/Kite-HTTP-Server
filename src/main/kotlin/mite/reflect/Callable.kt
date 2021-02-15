package mite.reflect

import kotlin.reflect.*
import kotlin.reflect.jvm.*

class Callable(val target:Any, val member: KCallable<*>) {

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

    fun call() =
        try {
            member.isAccessible = true
            member.call(target)
        } catch (t: Throwable) {
            t
        }

}