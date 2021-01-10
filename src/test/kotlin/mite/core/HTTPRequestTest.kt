package mite.core

import org.junit.Test
import kotlin.test.*

class HTTPRequestTest {

    @Test
    fun `HTTP version is set when parsing fails`() {
        val string = toString()
        val request: HTTPRequest = parse(string)
        assertEquals("Unknown", request.httpVersion.version)
        assertFalse(request.httpVersion.mimeAware)
    }

    @Test
    fun `raw string is set when parsing fails`() {
        val string = toString()
        val request: HTTPRequest = parse(string)
        assertSame(string, request.raw[0])
    }

    @Test
    fun `method is set from request`() {
        assertEquals("GET", parse("GET /").method)
        assertEquals("GET", parse("GET /whatever").method)
        assertEquals("GET", parse("GET /foo HTTP/1.0").method)
        assertEquals("GET", parse("GET / HTTP/1.1").method)

        assertEquals("POST", parse("POST /").method)
    }

    @Test
    fun `filename is set from request`() {
        assertEquals("/", parse("GET /").filename)
        assertEquals("/whatever", parse("GET /whatever").filename)
        assertEquals("/foo", parse("GET /foo HTTP/1.0").filename)
        assertEquals("/", parse("GET / HTTP/1.1").filename)
    }

    @Test
    fun `httpVersion is set from request`() {
        assertEquals("Unknown", parse("GET /").httpVersion.toString())
        assertEquals("Unknown", parse("GET /whatever").httpVersion.toString())
        assertEquals("HTTP/1.0", parse("GET /foo HTTP/1.0").httpVersion.toString())
        assertEquals("HTTP/1.1", parse("GET / HTTP/1.1").httpVersion.toString())
    }


    @Test
    fun `values are set from URL encoded POST`() {
val request = parse("""
POST /test HTTP/1.1
Host: foo.example
Content-Type: application/x-www-form-urlencoded
Content-Length: 27

field1=value1&field2=value2
""".trimIndent())

        assertEquals("HTTP/1.1",request.httpVersion.toString())
        assertEquals("/test?field1=value1&field2=value2",request.filename)
        assertEquals("POST",request.method)

    }

    @Test
    fun `values are set from multipart form-data POST`() {
val request = parse("""
POST /test HTTP/1.1
Host: foo.example
Content-Type: multipart/form-data;boundary="boundary"

--boundary
Content-Disposition: form-data; name="field1"

value1
--boundary
Content-Disposition: form-data; name="field2"; filename="example.txt"

value2
--boundary--
""".trimIndent())

        assertEquals("HTTP/1.1",request.httpVersion.toString())
        assertEquals("/test?field1=value1&field2=value2",request.filename)
        assertEquals("POST",request.method)

    }

    //    POST /test HTTP/1.1
//    Host: foo.example
//    Content-Type: application/x-www-form-urlencoded
//    Content-Length: 27
//
//    field1=value1&field2=value2

//    A form using the multipart/form-data content type:
//
//    POST /test HTTP/1.1
//    Host: foo.example
//    Content-Type: multipart/form-data;boundary="boundary"
//
//    --boundary
//    Content-Disposition: form-data; name="field1"
//
//    value1
//    --boundary
//    Content-Disposition: form-data; name="field2"; filename="example.txt"
//
//    value2
//    --boundary--

    companion object {
        fun parse(request: String): HTTPRequest {
            return HTTPRequest.parse(arrayOf(request))
        }
    }
}