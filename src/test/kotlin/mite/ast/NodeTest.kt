package mite.ast

import org.junit.Test
import kotlin.test.*

class NodeTest {

    val kind = Node::class
    val value = "bar"

    @Test
    fun `leaf`() {
        val node = Node.leaf(kind,value)

        assertEquals(kind, node.kind)
        assertEquals(Node.Arity.leaf,node.arity)
        assertEquals(value,node.leaf)
        assertEquals(value,node.value)
    }

    @Test
    fun `list`() {
        val node = Node.list(kind,listOf(3,5,7,9))

        assertEquals(kind, node.kind)
        assertEquals(Node.Arity.list,node.arity)
        val list = node.list!!
        assertEquals(4, list.size)
        assertEquals(3, list.get(0).leaf)
        assertEquals(list,node.value)
    }

    @Test
    fun `map`() {
        val node = Node.map(kind,mapOf(
            2 to 4,
            3 to 6
        ))

        assertEquals(kind, node.kind)
        assertEquals(Node.Arity.map,node.arity)
        val map = node.map!!
        assertEquals(4, map.get(2)!!.leaf)
        assertEquals(map,node.value)
    }

    @Test
    fun `map of kind`() {
        val node = Node.mapOfKind(kind,
            3 to 9,
            4 to 16
        )

        assertEquals(kind, node.kind)
        assertEquals(Node.Arity.map,node.arity)
        val map = node.map!!
        assertEquals(9, map.get(3)!!.leaf)
        assertEquals(16, map.get(4)!!.leaf)
        assertEquals(map,node.value)
    }

    @Test
    fun `map entries when root is list of maps`() {
        val node = Node.list(kind,
            listOf(
                Node.map(kind, mapOf(
                    "first" to 1,
                    "second" to 2
                )),
                Node.map(kind, mapOf(
                    "1" to "gun",
                    "2" to "shoe"
                ))
            ))

        val list = node.list!!
        assertEquals(2,list.size)
        val map1 = list[0].map!!
        assertEquals(2,map1.size)
        assertEquals(1,      map1["first"])
        assertEquals(2,      map1["second"])
        assertEquals("gun",  node.list!![1].map!!["1"])
        assertEquals("Shoe", node.list!![1].map!!["2"])
    }

}