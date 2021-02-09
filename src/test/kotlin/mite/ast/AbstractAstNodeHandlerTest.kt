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
            override fun node(request: HTTP.Request): Node = Node.leaf(kind,entry)
        }
        val root = handler.node(request("/root"))

        assertEquals(entry, root.leaf)
    }

    @Test
    fun `root is list of entries`() {
        val handler = object : AbstractAstNodeHandler("/root",Log.Entry::class) {
            override fun node(request: HTTP.Request): Node = Node.list(kind,listOf(entry))
        }
        val root = handler.node(request("/root"))

        val list = root.list!!
        assertEquals(1, list.size)
        assertEquals(entry, list[0].leaf)
    }

    @Test
    fun `root is map of entries`() {
        val handler = object : AbstractAstNodeHandler("/root",Log.Entry::class) {
            override fun node(request: HTTP.Request): Node = Node.map(kind,mapOf("entry" to entry))
        }
        val root = handler.node(request("/root"))

        val map = root.map!!
        assertEquals(1, map.size)
        assertEquals(entry, map["entry"]!!.leaf)
    }

}