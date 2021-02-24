package mite.ast

import mite.reflect.Callable
import kotlin.reflect.*

/**
 * Uses reflection to render objects of the given class.
 */
data class ClassSpecificReflectionNodeRenderer(val kind: KClass<*>) {

    private fun props() = kind.members
        .filter { member -> Callable("",member).couldBeCalledWithRightValue()}
        .filter { member -> member.name != "hashCode" }
        .filter { member -> member.name != "toString" }

    fun header() = props().map { x -> x.name }

    fun render(node: Node) =
        if (kind.isInstance(node.value)) renderPropValues(node) else renderBadArgumentMessages(node.value)

    private fun renderBadArgumentMessages(value: Any) = props().map { x -> "$value is not a $kind" }

    private fun renderPropValues(node: Node) = props().map { x -> value(node,x) }

    private fun value(node: Node,member:KCallable<*>) = Callable(node.value,member).call().toString()

}