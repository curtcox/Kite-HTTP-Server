package mite.http

import mite.core.ExchangeTracker
import org.junit.Test
import kotlin.test.assertEquals

class RequestParserTest {

    val parser = RequestParser

    fun parse(s:String) = parser.parse(HTTP.Request.Raw(arrayOf(s)))

    @Test
    fun `filename is set from request`() {
        ExchangeTracker.nextInfo()
        assertEquals("/", parse("GET /").filename)
        assertEquals("/?", parse("GET /?").filename)
        assertEquals("/whatever", parse("GET /whatever").filename)
        assertEquals("/foo", parse("GET /foo HTTP/1.0").filename)
        assertEquals("/foo?bar", parse("GET /foo?bar HTTP/1.0").filename)
        assertEquals("/", parse("GET / HTTP/1.1").filename)
        assertEquals("/bin?baz", parse("GET /bin?baz HTTP/1.1").filename)
    }

}