package mite.ast

data class ReflectiveNode(val nodeValue:Any) : Node {

    override val map: Map<String, Node> get() =  props()
        .map { member -> Pair(member.name,node(member.call(nodeValue))) }
        .toMap()

    override val list: List<Node> get() = TODO("Not yet implemented")

    override val leaf: Any get() = TODO("Not yet implemented")

    override val value: Any get() = nodeValue

    override val arity: Node.Arity get() = Node.Arity.map

    private fun props() = nodeValue::class.members
        .filter { member -> member.parameters.size == 1 }
        .filter { member -> !member.name.startsWith("component") }

    private fun node(value : Any?) =
        if (value==null) SimpleNode.leaf(Node::class,"null")
        else ReflectiveNode(value)

}