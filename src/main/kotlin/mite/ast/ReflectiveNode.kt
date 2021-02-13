package mite.ast

import kotlin.reflect.*
import kotlin.reflect.jvm.*

/**
 * A wrapper to turn a value into a Node via reflection.
 */
data class ReflectiveNode(val nodeValue:Any) : Node {

    override val map: Map<String, Node> get() = props()
        .map { member -> Pair(member.name,node(call(member))) }
        .toMap()

    override val list: List<Node> get() = TODO("Not yet implemented")

    override val leaf: Any get() = TODO("Not yet implemented")

    override val value: Any get() = nodeValue

    override val arity: Node.Arity get() = Node.Arity.map

    private fun props() = nodeValue::class.members.filter { member -> weShouldCall(member) }

    // There must be a better way to determine if a callable can safely be called and yet here we are.
    private fun weShouldCall(member: KCallable<*>) =
        try {
            member.returnType.toString()!="kotlin.Unit" &&
            member.parameters.size == 1 &&
            valueTypeMatches(member.parameters[0].type) &&
            !member.name.startsWith("component")
        } catch (t: Throwable) {
            false // backoff if anything throws an exception
        }

    private fun valueTypeMatches(t : KType) =
      declassify(t) == declassify(nodeValue::class) || declassify(t.javaType) == declassify(nodeValue::class)

    private fun declassify(c: Any) = c.toString().replace("class ","")

    private fun node(value : Any?) =
        if (value==null) SimpleNode.leaf(Node::class,"null")
        else ReflectiveNode(value)

    private fun call(member: KCallable<*>) =
        try {
            member.isAccessible = true
            member.call(nodeValue)
        } catch (t: Throwable) {
            t
        }
}