package mite.html

import org.junit.Test
import kotlin.test.*

class ThrowableRendererTest {

    val renderer = ThrowableRenderer

    @Test
    fun `render returns a table for throwables`() {
        val html = renderer.render(Throwable())
        assertTrue(html is Table)
    }

    @Test
    fun `render returns a table header matching stack trace`() {
        val t = Throwable()
        val table = renderer.render(t) as Table
        val head = table.head
        val cells = head.cells
        assertEquals(3,cells.size)
        assertEquals("Class Name",cells[0])
        assertEquals("File Name",cells[1])
        assertEquals("line number",cells[2])
    }

    @Test
    fun `render returns a table body matching stack trace`() {
        val t = Throwable()
        val stack = t.stackTrace
        val table = renderer.render(t) as Table
        val rows = table.body.rows
        assertEquals(stack.size,rows.size)
        val cells = rows[0].cells
        assertEquals("mite.html.ThrowableRendererTest",cells[0])
        assertEquals("""<a href="/source/mite/html/ThrowableRendererTest.kt#30">ThrowableRendererTest.kt</a>""",cells[1])
        assertEquals("""<a href="/source/mite/html/ThrowableRendererTest.kt#30">30</a>""",cells[2])
    }

}