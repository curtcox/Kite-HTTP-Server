package mite.http

import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*

class RequestTest {

    @Test
    fun `HTTP version is set when parsing fails`() {
        val string = toString()
        val request: Request = parse(string)
        assertEquals("Unknown", request.httpVersion.version)
        assertFalse(request.httpVersion.mimeAware)
    }

    @Test
    fun `raw string is set when parsing fails`() {
        val string = toString()
        val request: Request = parse(string)
        assertSame(string, request.raw.lines[0])
    }

    @Test
    fun `method is set from request`() {
        val GET = Request.Method.GET
        val POST = Request.Method.POST
        assertEquals(GET, parse("GET /").method)
        assertEquals(GET, parse("GET /whatever").method)
        assertEquals(GET, parse("GET /foo HTTP/1.0").method)
        assertEquals(GET, parse("GET / HTTP/1.1").method)

        assertEquals(POST, parse("POST /").method)
    }

    @Test
    fun `filename is set from request`() {
        assertEquals("/", parse("GET /").filename)
        assertEquals("/?", parse("GET /?").filename)
        assertEquals("/whatever", parse("GET /whatever").filename)
        assertEquals("/foo", parse("GET /foo HTTP/1.0").filename)
        assertEquals("/foo?bar", parse("GET /foo?bar HTTP/1.0").filename)
        assertEquals("/", parse("GET / HTTP/1.1").filename)
        assertEquals("/bin?baz", parse("GET /bin?baz HTTP/1.1").filename)
    }

    @Test
    fun `withoutPrefix will drop given prefix`() {
        assertEquals("/log", parse("GET /?log").withoutPrefix("/?").filename)
        assertEquals("/", parse("GET /?").withoutPrefix("/?").filename)
        assertEquals("/ls", parse("GET /exec/ls HTTP/1.0").withoutPrefix("/exec").filename)
        assertEquals("/in/the/course", parse("GET /when/in/the/course HTTP/1.1").withoutPrefix("/when").filename)
    }

    @Test
    fun `httpVersion is set from request`() {
        assertEquals(Version.Unknown, parse("GET /").httpVersion)
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
        assertEquals(Request.Method.POST,request.method)
        assertEquals("foo.example",request.host)
        assertEquals(ContentType.FORM_URLENCODED,request.contentType)
        assertEquals("/test?field1=value1&field2=value2",request.filename)

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

    @Test
    fun `typical GET`() {
    val request = parse("""
GET /log HTTP/1.1
Host: localhost:8000
Connection: keep-alive
Cache-Control: max-age=0
sec-ch-ua: "Google Chrome";v="87", " Not;A Brand";v="99", "Chromium";v="87"
sec-ch-ua-mobile: ?0
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36
Accept:
""".trimIndent())

        assertEquals("HTTP/1.1",request.httpVersion.toString())
        assertEquals("/log",request.filename)
        assertEquals(Request.Method.POST,request.method)
        assertEquals("localhost:8000",request.host)
        assertEquals(ContentType.FORM_URLENCODED,request.contentType)

    }

    companion object {
        fun parse(request: String) = Request.parse(Request.Raw(arrayOf(request)))
    }
}