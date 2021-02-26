package mite.ast

import mite.core.Log
import mite.http.HTTP
import org.junit.Test
import java.time.Instant
import kotlin.test.*

class ReflectionNodeRendererTest {

    val renderer = ReflectionNodeRenderer
    data class Infield(val first:String,val second:String,val third:String)
    val infield = Infield("Buckner","Sandberg","Nettles")
    val time = Instant.now()
    val logger = Infield::class
    val record = "stuff"
    val stack  = Throwable()
    val number = 42
    val entry = Log.Entry(number,time,logger,record,stack)

    @Test
    fun `infield header should match field names`() {
        val header = renderer.header(listOf(infield))

        assertEquals(3,header.size)

        assertTrue(header.contains("first"))
        assertTrue(header.contains("second"))
        assertTrue(header.contains("third"))
    }

    @Test
    fun `log entry header should match field names`() {
        val header = renderer.header(listOf(entry))

        assertEquals(5,header.size)

        assertTrue(header.contains("time"))
        assertTrue(header.contains("logger"))
        assertTrue(header.contains("record"))
        assertTrue(header.contains("stack"))
    }

    @Test
    fun `infield values should match field values`() {
        val values = renderer.render(SimpleNode.leaf(Infield::class,infield))

        assertEquals(3,values.size)

        assertEquals("Buckner", values[0])
        assertEquals("Sandberg",values[1])
        assertEquals("Nettles", values[2])
    }

    @Test
    fun `log entry values should match field values`() {

        val values = renderer.render(SimpleNode.leaf(Log.Entry::class,entry))

        assertEquals(5,values.size)

        assertEquals(number,             values[0])
        assertEquals(logger.toString(),  values[1])
        assertEquals(record,             values[2])
        assertEquals(stack.toString(),   values[3])
        assertEquals(time.toString(),    values[4])
    }

}