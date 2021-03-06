package mite.handlers

import mite.TestObjects
import mite.http.HTTP.*
import mite.core.*
import org.junit.Test
import kotlin.test.*

class FaviconHandlerTest {

    val favicon = FaviconHandler
    fun request(filename:String) = TestObjects.requestForFilename(filename)

    @Test
    fun `returns icon`() {
        val response = favicon.handle(request("/favicon.ico"))
        assertEquals(StatusCode.OK,response.status)
        assertEquals(ContentType.ICON,response.contentType)
        assertEquals(1406,(response.payload as ByteArray).size)
        assertTrue(response.contentType.binary)
    }
}