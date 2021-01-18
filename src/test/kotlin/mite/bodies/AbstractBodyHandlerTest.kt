package mite.bodies

import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*

class AbstractBodyHandlerTest {

    fun request(filename:String) =
        Request(arrayOf(),Request.Method.UNKNOWN,"",filename, ContentType.FORM_URLENCODED,Version.Unknown)

    val handler = object : AbstractBodyHandler("prefix") {
        override fun handle(request: Request): InternalResponse {
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

        assertEquals(ContentType.TEXT,response.contentType)
        assertEquals("prefix",response.payload)
        assertEquals(StatusCode.OK,response.status)
    }
}