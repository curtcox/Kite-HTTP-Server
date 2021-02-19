package mite.ast

import mite.core.Log
import kotlin.reflect.*

/**
 * Uses reflection to render objects of the given class.
 */
data class ReflectionRenderer(val kind: KClass<*>) : Node.Renderer {

    private fun props() = kind.members
        .filter { member -> member.parameters.size == 1 }
        .filter { member -> member.name != "hashCode" }
        .filter { member -> member.name != "toString" }
        .filter { member -> !member.name.startsWith("component") }

    override fun header() = props().map { x -> x.name }

    override fun render(node: Node) =
        if (kind.isInstance(node.value)) renderPropValues(node) else renderBadArgumentMessages(node.value)

    private fun renderBadArgumentMessages(value: Any) = props().map { x -> "$value is not a $kind" }

    private fun renderPropValues(node: Node) = props().map { x -> value(node,x) }

    private fun value(node: Node,member:KCallable<*>) = try {
        member.call(node.value).toString()
    } catch (t :Throwable) {
        Log.log(ReflectionRenderer::class,node,t)
        "$t while rendering ${node.value} as $kind"
    }

}