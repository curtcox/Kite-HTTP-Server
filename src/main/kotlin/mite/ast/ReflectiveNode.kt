package mite.ast

import kotlin.reflect.*
import kotlin.reflect.jvm.*
import mite.ast.Node.*

/**
 * A wrapper to turn a value into a Node via reflection.
 */
data class ReflectiveNode(val nodeValue:Any) : Node {

    override val map: Map<String, Node> get() =
        if (nodeValue is Map<*,*>) mapAsNodeMap(nodeValue)
        else propsAsMap()

    override val list: List<Node> get() = when (nodeValue) {
        is Iterable<*> -> listAsNodeList(nodeValue)
        is Array<*>    -> listAsNodeList(nodeValue.toList())
        else           -> throw IllegalArgumentException()
    }

    override val leaf: Any get() = TODO("Not yet implemented")

    override val value: Any get() = nodeValue

    override val arity: Arity get() = when (nodeValue) {
        is List<*>  -> Arity.list
        is Array<*> -> Arity.list
        else        -> Arity.map
    }
    
    private fun mapAsNodeMap(map:Map<*,*>) = map.map { e -> Pair(e.key.toString(),node(e.value))}.toMap()

    private fun listAsNodeList(list:Iterable<*>) = list.map { x -> node(x) }

    private fun props() = nodeValue::class.members.filter { member -> weShouldCall(member) }

    private fun propsAsMap(): Map<String, Node> = props()
        .map { member -> Pair(member.name,node(call(member))) }
        .toMap()

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