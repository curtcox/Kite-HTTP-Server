package mite

import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

class HTTPRequestTest {
    @Test
    fun httpVersion_is_set_when_parsing_fails() {
        val string = toString()
        val request: HTTPRequest = parse(string)
        assertEquals("Unknown", request.httpVersion.version)
        Assert.assertFalse(request.httpVersion.mimeAware)
    }

    @Test
    fun string_is_set_when_parsing_fails() {
        val string = toString()
        val request: HTTPRequest = parse(string)
        Assert.assertSame(string, request.string)
    }

    @Test
    fun method_is_set_from_request() {
        assertEquals("GET", parse("GET /").method)
        assertEquals("GET", parse("GET /whatever").method)
        assertEquals("GET", parse("GET /foo HTTP/1.0").method)
        assertEquals("GET", parse("GET / HTTP/1.1").method)
    }

    @Test
    fun filename_is_set_from_request() {
        assertEquals("/", parse("GET /").filename)
        assertEquals("/whatever", parse("GET /whatever").filename)
        assertEquals("/foo", parse("GET /foo HTTP/1.0").filename)
        assertEquals("/", parse("GET / HTTP/1.1").filename)
    }

    @Test
    fun httpVersion_is_set_from_request() {
        assertEquals("Unknown", parse("GET /").httpVersion.toString())
        assertEquals("Unknown", parse("GET /whatever").httpVersion.toString())
        assertEquals("HTTP/1.0", parse("GET /foo HTTP/1.0").httpVersion.toString())
        assertEquals("HTTP/1.1", parse("GET / HTTP/1.1").httpVersion.toString())
    }

    companion object {
        fun parse(request: String): HTTPRequest {
            return HTTPRequest.parse(request)
        }
    }
}