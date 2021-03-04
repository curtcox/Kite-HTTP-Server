package mite.handlers

import mite.TestObjects
import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*

class ResourceHandlerTest {

    val resources = ResourceHandler
    fun request(filename:String) = TestObjects.internalRequestForFilename(filename)

    @Test
    fun `returns icon`() {
        val response = resources.handle(request("/favicon.ico"))
        assertEquals(StatusCode.OK,response.status)
        assertTrue(response.payload is ByteArray)
        assertEquals(1406,(response.payload as ByteArray).size)
    }
}