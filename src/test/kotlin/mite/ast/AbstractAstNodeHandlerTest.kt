package mite.ast

import mite.TestObjects
import mite.core.Log
import mite.http.HTTP
import org.junit.Test
import java.time.Instant
import kotlin.reflect.KClass
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


    @Test
    fun `root is log entry`() {
        val handler = object : AbstractAstNodeHandler("/root",Log.Entry::class) {
            override fun root() = Node.leaf(kind,entry)
        }
        val root = handler.handle(request("/root"))

        assertEquals(HTTP.ContentType.AST, root.contentType)
        assertEquals(Node.leaf(kind,entry), root.payload)
    }

    @Test
    fun `list does not handle invalid index`() {
        val handler = object : AbstractAstNodeHandler("/root",Log.Entry::class) {
            override fun root(): Node = Node.list(kind,listOf(entry))
        }
        assertFalse(handler.handles(request("/root@1")))
    }

    @Test
    fun `list handles valid index`() {
        val handler = object : AbstractAstNodeHandler("/root",Log.Entry::class) {
            override fun root(): Node = Node.list(kind,listOf(entry))
        }
        assertTrue(handler.handles(request("/root@0")))
    }

    @Test
    fun `root when root is list of entries`() {
        val handler = object : AbstractAstNodeHandler("/root",Log.Entry::class) {
            override fun root(): Node = Node.list(kind,listOf(entry))
        }
        val root = handler.handle(request("/root")).payload as Node

        val list = root.list!!
        assertEquals(1, list.size)
        assertEquals(entry, list[0].leaf)
    }

    @Test
    fun `item when root is list of entries`() {
        val handler = object : AbstractAstNodeHandler("/root",Log.Entry::class) {
            override fun root(): Node = Node.list(kind,listOf(entry))
        }
        val node = handler.handle(request("/root@0")).payload as Node

        val leaf = node.leaf!!
        assertEquals(entry, leaf)
    }

    @Test
    fun `root when root is is map of entries`() {
        val handler = object : AbstractAstNodeHandler("/root",Log.Entry::class) {
            override fun root(): Node = Node.map(kind,mapOf("entry" to entry))
        }
        val root = handler.handle(request("/root")).payload as Node

        val map = root.map!!
        assertEquals(1, map.size)
        assertEquals(entry, map["entry"]!!.leaf)
    }

    @Test
    fun `value when root is is map of entries`() {
        val handler = object : AbstractAstNodeHandler("/root",Log.Entry::class) {
            override fun root(): Node = Node.map(kind,mapOf("entry" to entry))
        }
        val node = handler.handle(request("/root@entry")).payload as Node

        val leaf = node.leaf
        assertEquals(entry, leaf)
    }

}