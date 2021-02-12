package mite.ast

import mite.TestObjects
import mite.core.Log
import mite.http.HTTP
import org.junit.Test
import java.time.Instant
import kotlin.test.*

class AbstractAstNodeHandlerTest {

    val kind = Node::class
    val value = "bar"
    val time = Instant.now()
    val logger = kind
    val record = "record"
    val stack = Throwable()

    val entry = Log.Entry(time,logger,record,stack)

    fun request(filename:String) = TestObjects.requestForFilename(filename)

    class TestHandler(val root:Node) : AbstractAstNodeHandler("/root",Log.Entry::class) {
        override fun root() = root
        fun payload(filename:String) = (handle(TestObjects.requestForFilename(filename)).payload as Node).value!!
    }

    @Test
    fun `root is log entry`() {
        val handler = TestHandler(Node.leaf(kind,entry))
        val root = handler.handle(request("/root"))

        assertEquals(HTTP.ContentType.AST, root.contentType)
        assertEquals(Node.leaf(kind,entry), root.payload)
    }

    @Test
    fun `list does not handle invalid index`() {
        val handler = TestHandler(Node.list(kind,listOf(entry)))
        assertFalse(handler.handles(request("/root@1")))
    }

    @Test
    fun `list handles valid index`() {
        val handler = TestHandler(Node.list(kind,listOf(entry)))
        assertTrue(handler.handles(request("/root@0")))
    }

    @Test
    fun `root when root is list of entries`() {
        val handler = TestHandler(Node.list(kind,listOf(entry)))
        val root = handler.payload("/root") as List<Node>

        val list = root
        assertEquals(1, list.size)
        assertEquals(entry, list[0].leaf)
    }

    @Test
    fun `item when root is list of entries`() {
        val handler = TestHandler(Node.list(kind,listOf(entry)))
        val node = handler.handle(request("/root@0")).payload as Node

        val leaf = node.leaf!!
        assertEquals(entry, leaf)
    }

    @Test
    fun `fields of item when root is list of entries`() {
        val handler = TestHandler(Node.list(kind,listOf(entry)))

        assertEquals(entry,   (handler.payload("/root") as List<Node>)[0].leaf)
        assertEquals(entry,   handler.payload("/root@0"))
        assertEquals(time,    handler.payload("/root@0@time"))
        assertEquals(logger,  handler.payload("/root@0@logger"))
        assertEquals(record,  handler.payload("/root@0@record"))
        assertEquals(stack,   handler.payload("/root@0@stack"))
    }

    @Test
    fun `map entries when root is list of maps`() {
        val handler = TestHandler(Node.list(kind,
            listOf(
                Node.map(kind,mapOf(
                    "first"  to 1,
                    "second" to 2
                )),
                Node.map(kind,mapOf(
                    "1" to "gun",
                    "2" to "shoe"
                ))
            )))

        assertEquals(1,      handler.payload("/root@0@first"))
        assertEquals(2,      handler.payload("/root@0@second"))
        assertEquals("gun",  handler.payload("/root@1@1"))
        assertEquals("shoe", handler.payload("/root@1@2"))
    }

    @Test
    fun `root when root is is map of entries`() {
        val handler = TestHandler(Node.map(kind,mapOf("entry" to entry)))
        val root = handler.handle(request("/root")).payload as Node

        val map = root.map!!
        assertEquals(1, map.size)
        assertEquals(entry, map["entry"]!!.leaf)
    }

    @Test
    fun `value when root is is map of entries`() {
        val handler = TestHandler(Node.map(kind,mapOf("entry" to entry)))
        val node = handler.handle(request("/root@entry")).payload as Node

        val leaf = node.leaf
        assertEquals(entry, leaf)
    }

}