package mite.ast

import mite.core.Log
import org.junit.Test
import java.time.Instant
import kotlin.test.*

class ReflectionRendererTest {

    val renderer = ReflectionRenderer()
    data class Infield(val first:String,val second:String,val third:String)
    val infield = Infield("Buckner","Sandberg","Nettles")
    val time = Instant.now()
    val logger = Infield::class
    val record = "stuff"
    val stack  = Throwable()
    val entry = Log.Entry(time,logger,record,stack)

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

        assertEquals(4,header.size)

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

        assertEquals(4,values.size)

        assertEquals(logger.toString(),  values[0])
        assertEquals(record,             values[1])
        assertEquals(stack.toString(),   values[2])
        assertEquals(time.toString(),    values[3])
    }

}