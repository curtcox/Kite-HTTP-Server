package mite

import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*

class DefaultHandlerTest {

    val handler = DefaultHandler

    fun forFilename(filename:String) =
        Request(Request.Raw(arrayOf()),Request.Method.UNKNOWN,"",filename,ContentType.FORM_URLENCODED,Version.Unknown)

    @Test
    fun `no headers for unknown version`() {
        val request = forFilename("/favicon.ico")
        val response = handler.handle(request)
        val headers = handler.handleHeaders(request,response)
        assertEquals(0,headers.size)
    }

    @Test
    fun `favicon is icon`() {
        val request = forFilename("/favicon.ico")
        val response = handler.handle(request)
        assertEquals(ContentType.ICON,response.contentType)
    }

    @Test
    fun `log is AST list`() {
        val request = forFilename("/log")
        val response = handler.handle(request)
        assertEquals(ContentType.HTML,response.contentType)
        assertEquals(StatusCode.OK,response.status)
    }

    @Test
    fun `pwd is text`() {
        val request = forFilename("/pwd")
        val response = handler.handle(request)
        assertEquals(ContentType.TEXT,response.contentType)
        assertEquals(StatusCode.OK,response.status)
        val page = response.page
        assertTrue(page.contains("/Users"),page)
        assertTrue(page.contains("/Kite-HTTP-Server"),page)
    }

}