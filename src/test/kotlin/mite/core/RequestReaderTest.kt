package mite.core

import org.junit.Test
import java.io.ByteArrayInputStream
import java.util.*
import kotlin.test.*

class RequestReaderTest {

    @Test
    fun `A URL encoded form`() {
        test("""
    POST /test HTTP/1.1
    Host: foo.example
    Content-Type: application/x-www-form-urlencoded
    Content-Length: 27

    field1=value1&field2=value2
        """.trimIndent())
    }

    @Test
    fun `A multipart form-data encoded form`() {
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
        assertEquals(lines, read.asList())
    }

}