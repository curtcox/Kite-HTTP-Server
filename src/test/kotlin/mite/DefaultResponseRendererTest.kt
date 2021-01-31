package mite

import mite.bodies.ProcessRequestHandler
import mite.core.Log
import mite.http.HTTP.*
import org.junit.Test
import java.io.File
import kotlin.test.*

class DefaultResponseRendererTest {

    val renderer    = DefaultResponseRenderer
    val OK_message  = InternalResponse.message("OK",StatusCode.OK)
    val BAD_message = InternalResponse.message("BAD",StatusCode.UNAUTHORIZED)
    val favicon     = InternalResponse.OK(File("favicon.ico").readBytes(),ContentType.ICON)

    fun forFilename(filename:String) =
        Request(Request.Raw(arrayOf()),Request.Method.UNKNOWN,"",filename,ContentType.FORM_URLENCODED,Version.Unknown)

    @Test
    fun `handles everything`() {
        assertTrue(renderer.handles(forFilename("/"),OK_message))
        assertTrue(renderer.handles(forFilename("/"),BAD_message))
    }

    @Test
    fun `favicon is icon`() {
        val request = forFilename("/favicon.ico")
        val response = renderer.render(request,favicon)
        assertEquals(ContentType.ICON,response.contentType)
    }

    @Test
    fun `log is HTML list`() {
        val request = forFilename("/log")
        val response = renderer.render(request,Log.handle(request))
        assertEquals(ContentType.HTML,response.contentType)
        assertEquals(StatusCode.OK,response.status)
    }

    @Test
    fun `pwd is text`() {
        val request = forFilename("/pwd")
        val response = renderer.render(request,ProcessRequestHandler.of().handle(request)!!)
        assertEquals(ContentType.TEXT,response.contentType)
        assertEquals(StatusCode.OK,response.status)
        val page = response.page
        assertTrue(page.contains("/Users"),page)
        assertTrue(page.contains("/Kite-HTTP-Server"),page)
    }

}