package mite.ast

import mite.ast.Node.*
import mite.core.Log
import org.junit.Test
import java.time.Instant
import kotlin.test.*

class ReflectiveNodeTest {

    @Test
    fun `string values are maps`() {
        val string = "wah?"
        val node = ReflectiveNode(string)

        assertEquals(Arity.map,   node.arity)
        assertEquals(9,  node.map.size)
        assertEquals(string,      node.value)

        assertEquals(ReflectiveNode(string),            node.map["toString"])
        assertEquals(ReflectiveNode(string.hashCode()), node.map["hashCode"])
        assertEquals(ReflectiveNode(string.length),     node.map["length"])
    }

    @Test
    fun `entry values are maps`() {
        val kind = Node::class
        val number = 42
        val time = Instant.now()
        val logger = kind
        val record = "record"
        val stack = Throwable()

        val entry = Log.Entry(number,time,logger,record,stack)
        val node = ReflectiveNode(entry)

        assertEquals(Arity.map, node.arity)
        assertEquals(7,node.map.size)
        assertEquals(entry,     node.value)

        assertEquals(ReflectiveNode(entry.toString()), node.map["toString"])
        assertEquals(ReflectiveNode(entry.hashCode()), node.map["hashCode"])
        assertEquals(ReflectiveNode(entry.time),       node.map["time"])
        assertEquals(ReflectiveNode(entry.logger),     node.map["logger"])
        assertEquals(ReflectiveNode(entry.record),     node.map["record"])
        assertEquals(ReflectiveNode(entry.stack),      node.map["stack"])
    }

    @Test
    fun `maps are maps`() {
        val map = mapOf(
            "Tinker" to "Evers",
            "Evers" to "Chance"
        )
        val node = ReflectiveNode(map)

        assertEquals(Arity.map, node.arity)
        assertEquals(2,node.map.size)
        assertEquals(map,     node.value)

        assertEquals(ReflectiveNode("Evers"),  node.map["Tinker"])
        assertEquals(ReflectiveNode("Chance"), node.map["Evers"])

        // this is a map, so don't allow object names to collide with keys
        assertNull(node.map["size"])
        assertNull(node.map["toString"])
        assertNull(node.map["hashCode"])
    }

    @Test
    fun `lists are lists`() {
        val list = listOf("Who","What","IDKW")
        val node = ReflectiveNode(list)

        assertEquals(Arity.list, node.arity)
        assertEquals(3, node.list.size)
        assertEquals(list,       node.value)

        assertEquals(ReflectiveNode("Who"),  node.list[0])
        assertEquals(ReflectiveNode("What"), node.list[1])
        assertEquals(ReflectiveNode("IDKW"), node.list[2])
    }

    @Test
    fun `arrays are lists`() {
        val list = arrayOf("ho","hat","how")
        val node = ReflectiveNode(list)

        assertEquals(Arity.list, node.arity)
        assertEquals(3, node.list.size)
        assertEquals(list,       node.value)

        assertEquals(ReflectiveNode("ho"),  node.list[0])
        assertEquals(ReflectiveNode("hat"), node.list[1])
        assertEquals(ReflectiveNode("how"), node.list[2])
    }

}