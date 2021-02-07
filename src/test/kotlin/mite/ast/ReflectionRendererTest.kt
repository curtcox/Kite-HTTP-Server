package mite.ast

import mite.core.Log
import org.junit.Test
import java.time.Instant
import kotlin.test.*

class ReflectionRendererTest {

    data class Infield(val first:String,val second:String,val third:String)

    @Test
    fun `infield header should match field names`() {
        val renderer = ReflectionRenderer(Infield::class)

        val header = renderer.header()

        assertEquals(3,header.size)

        assertTrue(header.contains("first"))
        assertTrue(header.contains("second"))
        assertTrue(header.contains("third"))
    }

    @Test
    fun `log entry header should match field names`() {
        val renderer = ReflectionRenderer(Log.Entry::class)

        val header = renderer.header()

        assertEquals(4,header.size)

        assertTrue(header.contains("time"))
        assertTrue(header.contains("logger"))
        assertTrue(header.contains("record"))
        assertTrue(header.contains("stack"))
    }

    @Test
    fun `infield values should match field values`() {
        val infield = Infield("Buckner","Sandberg","Nettles")
        val renderer = ReflectionRenderer(Infield::class)
        val values = renderer.render(Node.leaf(Infield::class,infield))

        assertEquals(3,values.size)

        assertEquals("Buckner", values[0])
        assertEquals("Sandberg",values[1])
        assertEquals("Nettles", values[2])
    }

    @Test
    fun `log entry values should match field values`() {
        val time = Instant.now()
        val logger = Infield::class
        val record = "stuff"
        val stack  = Throwable()
        val entry = Log.Entry(time,logger,record,stack)
        val renderer = ReflectionRenderer(Log.Entry::class)

        val values = renderer.render(Node.leaf(Log.Entry::class,entry))

        assertEquals(4,values.size)

        assertEquals(logger.toString(),  values[0])
        assertEquals(record,             values[1])
        assertEquals(stack.toString(),   values[2])
        assertEquals(time.toString(),    values[3])
    }

}