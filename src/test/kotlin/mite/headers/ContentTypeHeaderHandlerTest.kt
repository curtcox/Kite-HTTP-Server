package mite.headers

import mite.TestObjects
import mite.core.MiteHTTPServer
import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*

/*
 Test for the exact right headers.
 This test exists, because the previous headers were interpreted as HTML by Chrome,
 but plaintext by Firefox.
 */
class ContentTypeHeaderHandlerTest {

    val handler = ContentTypeHeaderHandler
    val request = request("/")
    val response = Response.empty

    fun request(filename:String) = TestObjects.requestForFilename(filename)

    @Test
    fun `contains expected headers`() {
        val headers = handler.handleHeaders(request,response)
        assertTrue(headers.contains(Header("HTTP/1.0",response.status)))
        assertTrue(headers.contains(Header("Server:", MiteHTTPServer.NAME)))
        assertTrue(headers.contains(Header("Content-Type:",ContentType.TEXT.streamName)))
        assertEquals(1,headers.count { x -> x.key == "Date:" })
        assertTrue(headers.contains(Header("Content-Length:",response.bytes.size)))
    }

}