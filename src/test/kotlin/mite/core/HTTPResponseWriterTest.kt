package mite.core

import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*
import java.io.ByteArrayOutputStream

class HTTPResponseWriterTest {

    val writer = HTTPResponseWriter
    val unknown = Version.Unknown
    val _1_1 = Version._1_1
    val smallText = "Stuff I expect to be in the response"
    val mediumText = generateMediumText()

    private fun generateMediumText(): String {
        val out = StringBuilder()
        while (out.length<40960) {
            out.append("${out.length} $out")
        }
        return out.toString()
    }

    val header = Header("header key","header value")
    val headers = arrayOf(header)

    fun assertReasonableSmallBody(out:String) {
        assertContains(out, smallText)
    }

    fun assertReasonableMediumBody(out:String) {
        assertContains(out, mediumText)
    }

    fun assertNoHeaders(out:String) {
        assertFalse(out.contains("header"))
    }

    fun assertReasonableHeaders(out:String,body:String) {
        val key = header.key
        val value = header.value.toString()
        assertContains(out, key)
        assertContains(out, value)
        assertIsBeforeIn(key,body,out)
        assertIsBeforeIn(value,body,out)
    }

    private fun assertIsBeforeIn(first: String, next: String, text: String) {
        assertContains(text,first)
        assertContains(text,next)
        val message = "<<$first>> should be before <<$next>> in <<$text>>"
        assertTrue(text.indexOf(first) < text.indexOf(next),message)
    }

    fun assertContains(full:String,part:String) {
        assertTrue(full.contains(part),"<<$part>> NOT in <<$full>>")
    }

    @Test
    fun `write small text to output stream contains text when HTTP version unknown`() {
        val response = Response(Response.Body.OK(smallText), headers)
        val bytes = ByteArrayOutputStream()
        writer.write(unknown,response,bytes)
        val out = bytes.toString()

        assertReasonableSmallBody(out)
    }

    @Test
    fun `write medium text to output stream contains text when HTTP version unknown`() {
        val response = Response(Response.Body.OK(mediumText), headers)
        val bytes = ByteArrayOutputStream()
        writer.write(unknown,response,bytes)
        val out = bytes.toString()

        assertReasonableMediumBody(out)
    }

    @Test
    fun `write text to output stream contains text when HTTP version known`() {
        val response = Response(Response.Body.OK(smallText), headers)
        val bytes = ByteArrayOutputStream()
        writer.write(_1_1,response,bytes)
        val out = bytes.toString()

        assertReasonableSmallBody(out)
        assertReasonableHeaders(out,smallText)
    }

    @Test
    fun `write binary to output stream contains text when HTTP version unknown`() {
        val response = Response(Response.Body(smallText.toByteArray(Charsets.UTF_8),ContentType.ICON,StatusCode.OK), headers)
        val bytes = ByteArrayOutputStream()
        writer.write(unknown,response,bytes)
        val out = bytes.toString()

        assertReasonableSmallBody(out)
        assertNoHeaders(out)
    }

    @Test
    fun `write binary to output stream contains text when HTTP version known`() {
        val response = Response(Response.Body(smallText.toByteArray(Charsets.UTF_8),ContentType.ICON,StatusCode.OK), headers)
        val bytes = ByteArrayOutputStream()
        writer.write(_1_1,response,bytes)
        val out = bytes.toString()

        assertReasonableSmallBody(out)
        assertReasonableHeaders(out,smallText)
    }

}