package mite.bodies

import mite.core.*
import org.junit.Test
import kotlin.test.*

class AbstractBodyHandlerTest {

    fun request(filename:String) = HTTPRequest(arrayOf(),"",filename, HTTPVersion.Unknown)
    val handler = object : AbstractBodyHandler("prefix") {
        override fun handle(request: HTTPRequest): HTTPResponse {
            return HTTPResponse.OK(request.filename)
        }
    }

    fun handles(filename:String) = handler.handles(request(filename))

    @Test
    fun handles_expected_requests() {
        assertTrue(handles("prefix"))
        assertTrue(handles("prefix/and then some other stuff"))
    }

    @Test
    fun ignores_other_requests() {
        assertFalse(handles("Neelix"))
    }

    @Test
    fun response_is_HTML() {
        val response = handler.handle(request("prefix"))!!
        assertEquals(ContentType.HTML,response.contentType)
        assertEquals("prefix",response.page)
    }
}