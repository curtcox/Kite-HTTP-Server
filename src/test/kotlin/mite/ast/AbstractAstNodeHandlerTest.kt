package mite.ast

import mite.TestObjects
import mite.core.Log
import mite.http.HTTP
import mite.ihttp.InternalHttp.*
import org.junit.Test
import java.time.Instant
import kotlin.test.*

class AbstractAstNodeHandlerTest {

    val kind = Node::class
    val value = "bar"
    val number = 86
    val time = Instant.now()
    val logger = kind
    val record = "record"
    val stack = Throwable()

    val entry = Log.Entry(number,time,logger,record,stack)

    fun request(filename:String) = TestObjects.internalRequestForFilename(filename)

    class TestHandler(val root:Node) : AbstractAstNodeHandler("/root") {
        override fun root(request: InternalRequest) = root
        fun payload(filename:String) = (handle(TestObjects.internalRequestForFilename(filename)).payload as Node).value
    }

    @Test
    fun `root is log entry`() {
        val handler = TestHandler(SimpleNode.leaf(kind,entry))
        val root = handler.handle(request("/root"))

        assertTrue(root.payload is Node)
        assertEquals(SimpleNode.leaf(kind,entry), root.payload)
    }

    @Test
    fun `list claims to handle invalid index`() {
        val handler = TestHandler(SimpleNode.list(kind,listOf(entry)))
        assertTrue(handler.handles(request("/root@1")))
    }

    @Test
    fun `list handles valid index`() {
        val handler = TestHandler(SimpleNode.list(kind,listOf(entry)))
        assertTrue(handler.handles(request("/root@0")))
    }

    @Test
    fun `root when root is list of entries`() {
        val handler = TestHandler(SimpleNode.list(kind,listOf(entry)))
        val root = handler.payload("/root") as List<Node>

        val list = root
        assertEquals(1, list.size)
        assertEquals(entry, list[0].leaf)
    }

    @Test
    fun `item when root is list of entries`() {
        val handler = TestHandler(SimpleNode.list(kind,listOf(entry)))
        val node = handler.handle(request("/root@0")).payload as Node

        val leaf = node.leaf
        assertEquals(entry, leaf)
    }

    @Test
    fun `fields of item when root is list of simple entries`() {
        val handler = TestHandler(SimpleNode.list(kind,listOf(entry)))

        assertEquals(entry,   (handler.payload("/root") as List<Node>)[0].leaf)
        assertEquals(entry,   handler.payload("/root@0"))
    }

    @Test
    fun `fields of item when root is lreflective ist of entries`() {
        val handler = TestHandler(ReflectiveNode(listOf(entry)))

        assertEquals(entry,   (handler.payload("/root") as List<Log.Entry>)[0])
        assertEquals(entry,   handler.payload("/root@0"))
        assertEquals(time,    handler.payload("/root@0@time"))
        assertEquals(logger,  handler.payload("/root@0@logger"))
        assertEquals(record,  handler.payload("/root@0@record"))
        assertEquals(stack,   handler.payload("/root@0@stack"))
    }

    @Test
    fun `map entries when root is list of maps`() {
        val handler = TestHandler(SimpleNode.list(kind,
            listOf(
                SimpleNode.map(kind,mapOf(
                    "first"  to 1,
                    "second" to 2
                )),
                SimpleNode.map(kind,mapOf(
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
        val handler = TestHandler(SimpleNode.map(kind,mapOf("entry" to entry)))
        val root = handler.handle(request("/root")).payload as Node

        val map = root.map
        assertEquals(1, map.size)
        assertEquals(entry, map["entry"]!!.leaf)
    }

    @Test
    fun `value when root is is map of entries`() {
        val handler = TestHandler(SimpleNode.map(kind,mapOf("entry" to entry)))
        val node = handler.handle(request("/root@entry")).payload as Node

        val leaf = node.leaf
        assertEquals(entry, leaf)
    }

}