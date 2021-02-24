package mite.ast

import mite.core.Log
import org.junit.Test
import java.time.Instant
import kotlin.test.*

class ClassSpecificReflectionNodeRendererTest {

    data class Infield(val first:String,val second:String,val third:String)

    @Test
    fun `infield header should match field names`() {
        val renderer = ClassSpecificReflectionNodeRenderer(Infield::class)

        val header = renderer.header()

        assertEquals(3,header.size)

        assertTrue(header.contains("first"))
        assertTrue(header.contains("second"))
        assertTrue(header.contains("third"))
    }

    @Test
    fun `log entry header should match field names`() {
        val renderer = ClassSpecificReflectionNodeRenderer(Log.Entry::class)

        val header = renderer.header()

        assertEquals(4,header.size)

        assertTrue(header.contains("time"))
        assertTrue(header.contains("logger"))
        assertTrue(header.contains("record"))
        assertTrue(header.contains("stack"))
    }

    @Test
    fun `stack trace element header should match field names`() {
        val renderer = ClassSpecificReflectionNodeRenderer(StackTraceElement::class)

        val header = renderer.header()

        assertEquals(22,header.size)

        assertTrue(header.contains("lineNumber"))
        assertTrue(header.contains("fileName"))
        assertTrue(header.contains("methodName"))
        assertTrue(header.contains("moduleName"))
        assertTrue(header.contains("classLoaderName"))
        assertTrue(header.contains("declaringClassObject"))
    }

    @Test
    fun `infield values should match field values`() {
        val infield = Infield("Buckner","Sandberg","Nettles")
        val renderer = ClassSpecificReflectionNodeRenderer(Infield::class)
        val values = renderer.render(SimpleNode.leaf(Infield::class,infield))

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
        val number = 42
        val entry = Log.Entry(number,time,logger,record,stack)
        val renderer = ClassSpecificReflectionNodeRenderer(Log.Entry::class)

        val values = renderer.render(SimpleNode.leaf(Log.Entry::class,entry))

        assertEquals(4,values.size)

        assertEquals(logger.toString(),  values[0])
        assertEquals(record,             values[1])
        assertEquals(stack.toString(),   values[2])
        assertEquals(time.toString(),    values[3])
    }

    @Test
    fun `stack trace values should match field values`() {
        val stack  = Throwable()
        val clazz = StackTraceElement::class
        val renderer = ClassSpecificReflectionNodeRenderer(clazz)

        val values = renderer.render(SimpleNode.leaf(clazz,stack.getStackTrace()[0]))

        assertEquals(22,values.size)

        assertEquals("true",  values[0])
    }

    @Test
    fun `render returns IllegalArgumentException when asked to render a different class`() {
        val kind = Log.Entry::class
        val renderer = ClassSpecificReflectionNodeRenderer(kind)
        val value = Infield("Buckner", "Sandberg", "Nettles")
        val rendered = renderer.render(SimpleNode.leaf(Infield::class,value))
        assertEquals("$value is not a $kind",rendered[0])
    }

}