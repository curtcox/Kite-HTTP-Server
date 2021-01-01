package mite

import mite.core.*
import org.junit.Test
import kotlin.test.*

class DefaultHandlerTest {

    val handler = DefaultHandler

    @Test
    fun favicon_is_icon() {
        val request = HTTPRequest("","","/favicon.ico",HTTPVersion.Unknown)
        val response = handler.handle(request)!!
        assertEquals(ContentType.ICON,response.contentType)
    }

    @Test
    fun log_is_HTML() {
        val request = HTTPRequest("","","/log",HTTPVersion.Unknown)
        val response = handler.handle(request)!!
        assertEquals(ContentType.HTML,response.contentType)
        assertEquals("foo",response.page)
    }

    @Test
    fun pwd_is_text() {
        val request = HTTPRequest("","","/pwd",HTTPVersion.Unknown)
        val response = handler.handle(request)!!
        assertEquals(ContentType.TEXT,response.contentType)
        assertEquals("foo",response.page)
    }

}