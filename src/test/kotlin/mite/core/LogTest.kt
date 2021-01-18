package mite.core

import mite.ast.Node
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
        assertEquals(ContentType.AST,response.contentType)
        assertEquals(StatusCode.OK,response.status)
        assertTrue(response.payload is Node)
    }

}