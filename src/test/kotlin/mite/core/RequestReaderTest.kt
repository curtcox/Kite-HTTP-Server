package mite.core

import org.junit.Test
import kotlin.test.*

class RequestReaderTest {

    private val reader = RequestReader

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

    fun test(raw:String) {

    }

    // A simple form using the default application/x-www-form-urlencoded content type:
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

}