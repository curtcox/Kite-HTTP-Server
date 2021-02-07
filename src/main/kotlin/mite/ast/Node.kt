package mite.ast

import kotlin.reflect.KClass

/**
 * A node in a tree of objects. Nodes can be maps, lists, or leaves.
 * Node trees exist for three reasons:
 * 1) To provide a generic internal object representation that can be rendered into HTML, JSON, etc..
 * 2) To make subsequent intermediate processing easier than it would be on the rendered form.
 * 3) To provide a regular mechanism of mapping URL paths into object paths like:
 *    /log/15/stack/3/class
 */
data class Node(val kind: KClass<*>, val arity:Arity, val list: List<Node>?, val map:Map<Any,Node>?, val leaf:Any?) {

    interface Renderer {
        fun header() : List<String>
        fun render(node:Node) : List<String>
    }

    enum class Arity {
        list, map, leaf
    }

    companion object {
        fun node(kind:KClass<*>,value:Any) : Node {
            return when (value) {
                is Map<*, *> -> map(kind,  value as Map<Any, Any>)
                is   List<*> -> list(kind, value as List<Any>)
                else         -> leaf(kind,value)
            }
        }
        //fun leaf(value:String)              = Node(String::class,Arity.leaf,null,null,value)
        fun leaf(kind:KClass<*>,value:Any)       = Node(kind,Arity.leaf,null,null,value)
        fun map(kind:KClass<*>,map:Map<Any,Any>) =
            Node(kind,Arity.map,null,map.mapValues { node(kind,it.value) },null)
        fun mapOfKind(kind:KClass<*>,vararg pairs: Pair<Any,Any>) = map(kind,mapOf(*pairs))
        fun list(kind:KClass<*>,list:List<Any>)  =
            Node(kind,Arity.list,list.map { node(kind,it) },null,null)
    }
}