package mite.core

import org.junit.Test
import java.io.ByteArrayInputStream
import kotlin.test.*

class RequestReaderTest {

    @Test
    fun `1 line HTTP 1_0 GET request`() {
        test("""GET /4848 HTTP/1.0""")
    }

    @Test
    fun `HTTP 1_0 GET request`() {
        test("""
            GET /4848 HTTP/1.0
            Connection: Keep-Alive
            User-Agent: Mozilla/3.01 (X11; I; SunOS 5.4 sun4m)
            Pragma: no-cache
            Host: tecfa.unige.ch:7778
            Accept: image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, */*
            """.trimIndent())
    }

    @Test
    fun `HTTP 1_1 GET request`() {
        test("""
            GET / HTTP/1.1
            Host: www.example.com
        """.trimIndent())
    }

    @Test
    fun `HTTP 2 GET request`() {
        test("""
            GET /search?q=test HTTP/2
            Host: www.bing.com
            User-Agent: curl/7.54.0
            Accept: */*
        """.trimIndent())
    }

    @Test
    fun `HTTP 1_0 URL encoded form`() {
        test("""
            POST /cgi/4848 HTTP/1.0
            Referer: http://tecfa.unige.ch:7778/4848
            Connection: Keep-Alive
            User-Agent: Mozilla/3.01 (X11; I; SunOS 5.4 sun4m)
            Host: tecfa.unige.ch:7778
            Accept: image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, */*
            Content-type: application/x-www-form-urlencoded
            Content-length: 42

            name=Daniel&age=old&string=Hello+there+%21
        """.trimIndent())
    }
    @Test
    fun `HTTP 1_1 URL encoded form`() {
        test("""
    POST /test HTTP/1.1
    Host: foo.example
    Content-Type: application/x-www-form-urlencoded
    Content-Length: 27

    field1=value1&field2=value2
        """.trimIndent())
    }

    @Test
    fun `HTTP 1_1 multipart form-data encoded form`() {
        test("""
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
    }


    fun test(raw:String) {
        val lines = raw.split("\n")
        val standard = raw.replace("\n","\r\n")
        val read = RequestReader.readRequest(ByteArrayInputStream(standard.toByteArray()))
        assertEquals(lines, read.lines.asList())
    }

}