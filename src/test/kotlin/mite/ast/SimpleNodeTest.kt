package mite.ast

import org.junit.Test
import kotlin.test.*

class SimpleNodeTest {

    val kind = Node::class
    val value = "bar"

    @Test
    fun `leaf`() {
        val node = SimpleNode.leaf(kind,value)

        assertEquals(kind, node.kind)
        assertEquals(Node.Arity.leaf,node.arity)
        assertEquals(value,node.leaf)
        assertEquals(value,node.value)
    }

    @Test
    fun `list`() {
        val node = SimpleNode.list(kind,listOf(3,5,7,9))

        assertEquals(kind, node.kind)
        assertEquals(Node.Arity.list,node.arity)
        val list = node.list
        assertEquals(4, list.size)
        assertEquals(3, list.get(0).leaf)
        assertEquals(list,node.value)
    }

    @Test
    fun `map`() {
        val node = SimpleNode.map(kind,mapOf(
            2 to 4,
            3 to 6
        ))

        assertEquals(kind, node.kind)
        assertEquals(Node.Arity.map,node.arity)
        val map = node.map
        assertEquals(4, map.get(2)!!.leaf)
        assertEquals(6, map.get(3)!!.leaf)
        assertEquals(map,node.value)
    }

    @Test
    fun `map of kind`() {
        val node = SimpleNode.mapOfKind(kind,
            3 to 9,
            4 to 16
        )

        assertEquals(kind, node.kind)
        assertEquals(Node.Arity.map,node.arity)
        val map = node.map
        assertEquals(9, map.get(3)!!.leaf)
        assertEquals(16, map.get(4)!!.leaf)
        assertEquals(map,node.value)
    }

    @Test
    fun `map entries when root is list of maps`() {
        val node = SimpleNode.list(kind,
            listOf(
                SimpleNode.map(kind, mapOf(
                    "first" to 1,
                    "second" to 2
                )),
                SimpleNode.map(kind, mapOf(
                    "1" to "gun",
                    "2" to "shoe"
                ))
            ))

        val list = node.list
        assertEquals(2,list.size)
        val map1 = list[0].map
        assertEquals(2,map1.size)
        assertEquals(1,      map1["first"]!!.value)
        assertEquals(2,      map1["second"]!!.value)
        assertEquals("gun",  node.list[1].map["1"]!!.value)
        assertEquals("shoe", node.list[1].map["2"]!!.value)
    }

    @Test
    fun `map entries when root is map of lists`() {
        val node = SimpleNode.map(kind,
            mapOf(
                "1st" to SimpleNode.list(kind, listOf("alpha","beta")),
                "2nd" to SimpleNode.list(kind, listOf("compact","disc"))
            ))

        val map = node.map
        assertEquals(2,map.size)
        val list1 = map["1st"]!!.list
        assertEquals(2,list1.size)
        assertEquals("alpha",list1[0].value)
        assertEquals("beta", list1[1].value)
        assertEquals("compact",  node.map["2nd"]!!.list[0].value)
        assertEquals("disc",     node.map["2nd"]!!.list[1].value)
    }

}