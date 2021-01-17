package mite.core

import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*

class LogTest {

    fun request(filename:String) =
        Request(arrayOf(""),Request.Method.UNKNOWN,"",filename,ContentType.FORM_URLENCODED,Version.Unknown)

    @Test
    fun handles_expected_requests() {
        assertTrue(Log.handles(request("/log")))
    }

    @Test
    fun ignores_other_requests() {
        assertFalse(Log.handles(request("/schmog")))
    }

    @Test
    fun response_is_HTML() {
        val response = Log.handle(request("/log"))
        assertEquals(ContentType.HTML,response.contentType)
        val page = response
        assertEquals(page,"foo")
//        assertTrue(page.startsWith("<HTML>"),page)
//        assertTrue(page.endsWith("</HTML>"),page)
    }

}