package mite.headers

import mite.core.MiteHTTPServer
import mite.core.StatusCode
import java.io.*
import java.util.*

/**
 * MIME types
 */
enum class ContentType(val streamName: String) {

    HTML("text/html"),
    TEXT("text/plain"),
    GIF("image/gif"),
    CLASS("application/octet-stream"),
    JPEG("image/jpeg");

    @Throws(IOException::class)
    fun writeMIMEHeader(writer: Writer, status: StatusCode, length: Int) {
        writeln("HTTP 1.0 $status", writer)
        val now = Date()
        writeln("Date: $now", writer)
        writeln("Server: " + MiteHTTPServer.NAME, writer)
        writeln("Content-length: $length", writer)
        writeln("Content-type: $streamName", writer)
        writeln("", writer)
    }

    @Throws(IOException::class)
    private fun writeln(string: String, out: Writer) {
        out.write(string + "\r\n")
    }
}