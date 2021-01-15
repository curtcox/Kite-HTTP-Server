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
        assertEquals(value,node.leaf)
    }

    @Test
    fun map() {
        val node = Node.map(kind,mapOf(
            2 to 4,
            3 to 6
        ))

        assertEquals(kind, node.kind)
        assertEquals(4, node.map!!.get(2)!!.leaf)
    }

}