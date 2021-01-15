package mite.ast

data class Node(val kind:Any, val arity:Arity, val list: List<Node>?, val map:Map<Any,Node>?,val leaf:Any?) {

    enum class Arity {
        list, map, leaf
    }

    companion object {
        fun leaf(kind:Any,value:Any)        = Node(kind,Arity.leaf,null,null,value)
        fun map(kind:Any,map:Map<Any,Node>) = Node(kind,Arity.map,null,map,null)
        fun list(kind:Any,list:List<Node>)  = Node(kind,Arity.list,list,null,null)
    }
}