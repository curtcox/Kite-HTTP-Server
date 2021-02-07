package mite.ast

import kotlin.reflect.*

data class ReflectionRenderer(val kind: KClass<*>) : Node.Renderer {

    private fun props() = kind.members
        .filter { member -> member.parameters.size == 1 }
        .filter { member -> member.name != "hashCode" }
        .filter { member -> member.name != "toString" }
        .filter { member -> !member.name.startsWith("component") }

    override fun header() = props().map { x -> x.name }

    override fun render(node: Node) = props().map { x -> value(node,x) }

    private fun value(node: Node,member:KCallable<*>) = member.call(node.leaf).toString()

}