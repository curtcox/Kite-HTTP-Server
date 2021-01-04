package mite.core

import org.junit.Test
import kotlin.test.*
import java.io.ByteArrayOutputStream

class HTTPResponseWriterTest {

    val writer = HTTPResponseWriter
    val unknown = HTTPVersion.Unknown
    val _1_1 = HTTPVersion._1_1
    val text = "Stuff I expect to be in the response"
    val header = HTTPHeader("header key","header value")
    val headers = arrayOf(header)

    fun assertReasonableBody(out:String) {
        assertContains(out, text)
    }

    fun assertReasonableHeaders(out:String) {
        val key = header.key
        val value = header.value.toString()
        assertContains(out, key)
        assertContains(out, value)
    }

    fun assertContains(full:String,part:String) {
        assertTrue(full.contains(part),"<<$part>> NOT in <<$full>>")
    }

    @Test
    fun `write text to output stream contains text when HTTP version unknown`() {
        val response = HTTPResponse.OK(text)
        val bytes = ByteArrayOutputStream()
        writer.write(unknown,response,headers,bytes)
        val out = bytes.toString()

        assertReasonableBody(out)
    }

    @Test
    fun `write text to output stream contains text when HTTP version known`() {
        val response = HTTPResponse.OK(text)
        val bytes = ByteArrayOutputStream()
        writer.write(_1_1,response,headers,bytes)
        val out = bytes.toString()

        assertReasonableBody(out)
        assertReasonableHeaders(out)
    }

    @Test
    fun `write binary to output stream contains text when HTTP version unknown`() {
        val response = HTTPResponse.bytes(text.toByteArray(Charsets.UTF_8),ContentType.ICON)
        val bytes = ByteArrayOutputStream()
        writer.write(unknown,response,headers,bytes)
        val out = bytes.toString()

        assertReasonableBody(out)
    }

    @Test
    fun `write binary to output stream contains text when HTTP version known`() {
        val response = HTTPResponse.bytes(text.toByteArray(Charsets.UTF_8),ContentType.ICON)
        val bytes = ByteArrayOutputStream()
        writer.write(_1_1,response,headers,bytes)
        val out = bytes.toString()

        assertReasonableBody(out)
        assertReasonableHeaders(out)
    }

}