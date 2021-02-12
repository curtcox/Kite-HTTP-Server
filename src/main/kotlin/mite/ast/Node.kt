package mite.ast

import java.lang.IllegalStateException
import kotlin.reflect.KClass

/**
 * A node in a tree of objects. Nodes can be maps, lists, or leaves.
 * Node trees exist for three reasons:
 * 1) To provide a generic internal object representation that can be rendered into HTML, JSON, etc..
 * 2) To make subsequent intermediate processing easier than it would be on the rendered form.
 * 3) To provide a regular mechanism of mapping URL paths into object paths like:
 *    /log/15/stack/3/class
 */
data class Node(val kind: KClass<*>, val arity:Arity,
    private val listValue: List<Node>?, private val mapValue:Map<Any,Node>?, val leafValue:Any?)
{
    init {
        if (
            (leafValue == null && listValue == null && mapValue == null) ||
            (arity == Arity.leaf && leafValue == null) ||
            (arity == Arity.list && listValue == null) ||
            (arity == Arity.map  && mapValue  == null) ||
            (arity == Arity.leaf && leafValue is Node)
        ) {
            val message = "$kind $arity $listValue $mapValue $leafValue"
            throw IllegalArgumentException(message)
        }
    }

    val value : Any = when (arity) {
        Arity.list -> list
        Arity.map  -> map
        Arity.leaf -> leaf
    }

    val map  get() = if (arity==Arity.map)   mapValue!! else throw arityException(Arity.map)
    val list get() = if (arity==Arity.list) listValue!! else throw arityException(Arity.list)
    val leaf get() = if (arity==Arity.leaf) leafValue!! else throw arityException(Arity.leaf)

    private fun arityException(expected:Arity) = IllegalStateException("$arity is not $expected")

    interface Renderer {
        fun header() :          List<String>
        fun render(node:Node) : List<String>
    }

    enum class Arity {
        list, map, leaf
    }

    override fun toString() = when (arity) {
        Arity.list -> "List($list)"
        Arity.map  -> "Map($map)"
        Arity.leaf -> "Leaf($leaf)"
    }

    companion object {
        fun node(kind:KClass<*>,value:Any) : Node {
            return when (value) {
                is Map<*, *> -> map(kind,  value as Map<Any, Any>)
                is   List<*> -> list(kind, value as List<Any>)
                is      Node -> value
                else         -> leaf(kind, value)
            }
        }
        fun leaf(kind:KClass<*>,value:Any)       = Node(kind,Arity.leaf,null,null,value)
        fun map(kind:KClass<*>,map:Map<Any,Any>) =
            Node(kind,Arity.map,null,map.mapValues { node(kind,it.value) },null)
        fun mapOfKind(kind:KClass<*>,vararg pairs: Pair<Any,Any>) = map(kind,mapOf(*pairs))
        fun list(kind:KClass<*>,list:List<Any>)  =
            Node(kind,Arity.list,list.map { node(kind,it) },null,null)
    }

}