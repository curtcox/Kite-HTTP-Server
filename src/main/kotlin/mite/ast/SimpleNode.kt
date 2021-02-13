package mite.ast

import java.lang.IllegalStateException
import kotlin.reflect.KClass
import mite.ast.Node.*

/**
 * A node implementation that can produce arbitrary structures of arrays and trees.
 */
data class SimpleNode(val kind: KClass<*>, private val arityValue:Arity,
    private val listValue: List<Node>?, private val mapValue:Map<String,Node>?, val leafValue:Any?) : Node
{
    init {
        if (
            (leafValue == null && listValue == null && mapValue == null) ||
            (arity == Arity.leaf && leafValue == null) ||
            (arity == Arity.list && listValue == null) ||
            (arity == Arity.map  && mapValue  == null) ||
            (arity == Arity.leaf && leafValue is SimpleNode)
        ) {
            val message = "$kind $arity $listValue $mapValue $leafValue"
            throw IllegalArgumentException(message)
        }
    }

    override val value : Any = when (arity) {
        Arity.list -> list
        Arity.map  -> map
        Arity.leaf -> leaf
    }

    override val arity get() = arityValue
    override val map   get() = if (arity==Arity.map)   mapValue!! else throw arityException(Arity.map)
    override val list  get() = if (arity==Arity.list) listValue!! else throw arityException(Arity.list)
    override val leaf  get() = if (arity==Arity.leaf) leafValue!! else throw arityException(Arity.leaf)

    private fun arityException(expected:Arity) = IllegalStateException("$arity is not $expected")

    override fun toString() = when (arity) {
        Arity.list -> "List($list)"
        Arity.map  -> "Map($map)"
        Arity.leaf -> "Leaf($leaf)"
    }

    companion object {
        fun node(kind:KClass<*>,value:Any) : Node {
            return when (value) {
                is Map<*, *> -> map(kind,  value as Map<String, Any>)
                is   List<*> -> list(kind, value as List<Any>)
                is      Node -> value
                else         -> leaf(kind, value)
            }
        }
        fun leaf(kind:KClass<*>,value:Any)       = SimpleNode(kind,Arity.leaf,null,null,value)
        fun map(kind:KClass<*>,map:Map<String,Any>) =
            SimpleNode(kind,Arity.map,null,map.mapValues { node(kind,it.value) },null)
        fun mapOfKind(kind:KClass<*>,vararg pairs: Pair<String,Any>) = map(kind,mapOf(*pairs))
        fun list(kind:KClass<*>,list:List<Any>)  =
            SimpleNode(kind,Arity.list,list.map { node(kind,it) },null,null)
    }

}