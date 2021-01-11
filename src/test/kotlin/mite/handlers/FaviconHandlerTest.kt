package mite.handlers

import mite.core.*
import org.junit.Test
import kotlin.test.*

class FaviconHandlerTest {

    val favicon = FaviconHandler
    fun request(filename:String) = HTTPRequest(arrayOf(""),"",filename, HTTPVersion.Unknown)

    @Test
    fun `returns icon`() {
        val response = favicon.handle(request("/favicon.ico"))
        assertEquals(StatusCode.OK,response.status)
        assertEquals(ContentType.ICON,response.contentType)
        assertEquals(1406,response.bytes.size)
        assertTrue(response.contentType.binary)
    }
}