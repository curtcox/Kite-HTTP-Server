package mite.bodies

import mite.TestObjects
import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*
import mite.ihttp.InternalHttp.*

class AbstractBodyHandlerTest {

    fun request(filename:String) = TestObjects.internalRequestForFilename(filename)

    val handler = object : AbstractBodyHandler("prefix") {
        override fun handle(request: InternalRequest): InternalResponse {
            return InternalResponse.message(request.filename,StatusCode.OK)
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

        assertTrue(response.payload is String)
        assertEquals("prefix",response.payload)
        assertEquals(StatusCode.OK,response.status)
    }
}