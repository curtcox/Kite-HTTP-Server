package mite.ast

import org.junit.Test
import kotlin.test.*

class NodeTest {

    val kind = "foo"
    val value = "bar"

    @Test
    fun leaf() {
        val node = Node.leaf(kind,value)

        assertEquals(kind, node.kind)
        assertEquals(Node.Arity.leaf,node.arity)
        assertEquals(value,node.leaf)
    }

    @Test
    fun list() {
        val node = Node.list(kind,listOf(3,5,7,9))

        assertEquals(kind, node.kind)
        assertEquals(Node.Arity.list,node.arity)
        val list = node.list!!
        assertEquals(4, list.size)
        assertEquals(3, list.get(0).leaf)
    }

    @Test
    fun map() {
        val node = Node.map(kind,mapOf(
            2 to 4,
            3 to 6
        ))

        assertEquals(kind, node.kind)
        assertEquals(Node.Arity.map,node.arity)
        val map = node.map!!
        assertEquals(4, map.get(2)!!.leaf)
    }

    @Test
    fun mapOfKind() {
        val node = Node.mapOfKind(kind,
            3 to 9,
            4 to 16
        )

        assertEquals(kind, node.kind)
        assertEquals(Node.Arity.map,node.arity)
        val map = node.map!!
        assertEquals(9, map.get(3)!!.leaf)
        assertEquals(16, map.get(4)!!.leaf)
    }

}