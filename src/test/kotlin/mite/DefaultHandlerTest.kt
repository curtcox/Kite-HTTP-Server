package mite

import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*

class DefaultHandlerTest {

    val handler = DefaultHandler

    @Test
    fun favicon_is_icon() {
        val request = Request(arrayOf(),"","/favicon.ico",Version.Unknown)
        val response = handler.handle(request)!!
        assertEquals(ContentType.ICON,response.contentType)
    }

    @Test
    fun log_is_HTML() {
        val request = Request(arrayOf(),"","/log",Version.Unknown)
        val response = handler.handle(request)!!
        assertEquals(ContentType.HTML,response.contentType)
        val page = response.page
        assertTrue(page.contains("<HTML>"),page)
        assertTrue(page.contains("<TABLE>"),page)
        assertTrue(page.contains("<TH>Time</TH>"),page)
    }

    @Test
    fun pwd_is_text() {
        val request = Request(arrayOf(),"","/pwd",Version.Unknown)
        val response = handler.handle(request)!!
        assertEquals(ContentType.TEXT,response.contentType)
        val page = response.page
        assertTrue(page.contains("/Users"),page)
        assertTrue(page.contains("/Kite-HTTP-Server"),page)
    }

}