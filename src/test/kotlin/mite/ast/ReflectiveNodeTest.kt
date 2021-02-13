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

        assertEquals(Arity.map,  node.arity)
        assertEquals(11,node.map.size)
        assertEquals(string,     node.value)

        assertEquals(ReflectiveNode(string.toString()), node.map["toString"])
        assertEquals(ReflectiveNode(string.hashCode()), node.map["hashCode"])
        assertEquals(ReflectiveNode(string.length),     node.map["length"])
    }

    @Test
    fun `entry values are maps`() {
        val kind = Node::class
        val time = Instant.now()
        val logger = kind
        val record = "record"
        val stack = Throwable()

        val entry = Log.Entry(time,logger,record,stack)
        val node = ReflectiveNode(entry)

        assertEquals(Arity.map, node.arity)
        assertEquals(6,node.map.size)
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
        assertEquals(19,node.map.size)
        assertEquals(map,     node.value)

        assertEquals(ReflectiveNode(map.toString()),     node.map["toString"])
        assertEquals(ReflectiveNode(map.hashCode()),     node.map["hashCode"])
        assertEquals(ReflectiveNode(map.size),           node.map["size"])
        assertEquals(ReflectiveNode("Evers"),  node.map["Tinker"])
        assertEquals(ReflectiveNode("Chance"), node.map["Evers"])
    }

}