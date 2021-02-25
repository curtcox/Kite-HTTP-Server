package mite.http

import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*

class ResponseTest {

    @Test
    fun `empty has expected values`() {
        val empty = Response.Body.OK()
        assertEquals(0,empty.bytes.size)
        assertEquals("",empty.page)
        assertEquals(ContentType.TEXT,empty.contentType)
    }

    @Test
    fun `text has expected values`() {
        val text = Response.Body.OK("text",ContentType.TEXT)
        assertEquals(4,text.bytes.size)
        assertEquals("text",text.page)
        assertEquals(ContentType.TEXT,text.contentType)
    }

    @Test
    fun `HTML has expected values`() {
        val html = Response.Body.OK("<html></html>")
        assertEquals(13,html.bytes.size)
        assertEquals("<html></html>",html.page)
        assertEquals(ContentType.HTML,html.contentType)
    }

}