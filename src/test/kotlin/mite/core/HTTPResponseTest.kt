package mite.core

import org.junit.Test
import kotlin.test.*

class HTTPResponseTest {

    @Test
    fun `empty has expected values`() {
        val empty = HTTPResponse.empty
        assertEquals(0,empty.bytes.size)
        assertEquals("",empty.page)
        assertEquals(ContentType.TEXT,empty.contentType)
    }

    @Test
    fun `text has expected values`() {
        val text = HTTPResponse.OK("text",ContentType.TEXT)
        assertEquals(4,text.bytes.size)
        assertEquals("text",text.page)
        assertEquals(ContentType.TEXT,text.contentType)
    }

    @Test
    fun `HTML has expected values`() {
        val html = HTTPResponse.OK("<html></html>")
        assertEquals(13,html.bytes.size)
        assertEquals("<html></html>",html.page)
        assertEquals(ContentType.HTML,html.contentType)
    }

}