package mite

import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*

class DefaultHandlerTest {

    val handler = DefaultHandler

    fun forFilename(filename:String) =
        Request(arrayOf(),Request.Method.UNKNOWN,"",filename,ContentType.FORM_URLENCODED,Version.Unknown)

    @Test
    fun favicon_is_icon() {
        val request = forFilename("/favicon.ico")
        val response = handler.handle(request)!!
        assertEquals(ContentType.ICON,response.contentType)
    }

    @Test
    fun log_is_HTML() {
        val request = forFilename("/log")
        val response = handler.handle(request)!!
        assertEquals(ContentType.HTML,response.contentType)
        val page = response
        assertEquals(page,"foo")
//        assertTrue(page.contains("<HTML>"),page)
//        assertTrue(page.contains("<TABLE>"),page)
//        assertTrue(page.contains("<TH>Time</TH>"),page)
    }

    @Test
    fun pwd_is_text() {
        val request = forFilename("/pwd")
        val response = handler.handle(request)!!
        assertEquals(ContentType.TEXT,response.contentType)
        val page = response
        assertEquals(page,"foo")
//        assertTrue(page.contains("/Users"),page)
//        assertTrue(page.contains("/Kite-HTTP-Server"),page)
    }

}