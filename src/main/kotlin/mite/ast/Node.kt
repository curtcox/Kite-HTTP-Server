package mite.ast

data class Node(val kind:Any, val arity:Arity, val list: List<Node>?, val map:Map<Any,Node>?,val leaf:Any?) {

    interface Renderer {
        fun header() : String
        fun render(node:Node) : String
    }

    enum class Arity {
        list, map, leaf
    }

    companion object {
        fun node(kind:Any,value:Any) : Node {
            return when (value) {
                is Map<*, *> -> map(kind,  value as Map<Any, Any>)
                is   List<*> -> list(kind, value as List<Any>)
                else         -> leaf(kind,value)
            }
        }
        fun leaf(value:String)              = Node(String::class,Arity.leaf,null,null,value)
        fun leaf(kind:Any,value:Any)        = Node(kind,Arity.leaf,null,null,value)
        fun map(kind:Any,map:Map<Any,Any>) =
            Node(kind,Arity.map,null,map.mapValues { node(kind,it.value) },null)
        fun mapOfKind(kind:Any,vararg pairs: Pair<Any,Any>) = map(kind,mapOf(*pairs))
        fun list(kind:Any,list:List<Any>)  =
            Node(kind,Arity.list,list.map { node(kind,it) },null,null)
    }
}