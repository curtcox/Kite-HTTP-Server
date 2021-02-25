package mite.handlers

import mite.TestObjects
import mite.http.HTTP.*
import mite.core.*
import org.junit.Test
import kotlin.test.*

class PostHandlerTest {

    val inner = object : InternalHandler {
        override fun handles(request: Request): Boolean {
            TODO("Not yet implemented")
        }

        override fun handleHeaders(httpRequest: Request, response: Response.Body): Array<Header> {
            TODO("Not yet implemented")
        }

        override fun handle(request: Request): InternalResponse? {
            TODO("Not yet implemented")
        }

    }

    val postHandler = PostHandler(inner)

    fun filename(filename:String) = TestObjects.requestForFilename(filename)

    @Test
    fun `Only handles POST requests`() {
        assertTrue(postHandler.handles(filename("/POST/")))
        assertTrue(postHandler.handles(filename("/POST/withStuff")))

        assertFalse(postHandler.handles(filename("/POS")))
        assertFalse(postHandler.handles(filename("/POST")))
        assertFalse(postHandler.handles(filename("/POSTs/")))
        assertFalse(postHandler.handles(filename("POST")))
        assertFalse(postHandler.handles(filename("/post")))
    }

    @Test
    fun `A simple form using the default application x-www-form-urlencoded content type`() {

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