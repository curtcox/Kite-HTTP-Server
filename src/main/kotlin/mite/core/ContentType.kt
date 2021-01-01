package mite.core

import java.io.*
import java.util.*

/**
 * Content AKA MIME types
 */
enum class ContentType(val streamName: String, val binary:Boolean = true) {

    HTML("text/html",false),
    TEXT("text/plain",false),
    ICON("image/vnd.microsoft.icon"),
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

    companion object {
        fun auto(content:String) = if (seemsLikeHTML(content)) HTML else TEXT
        fun seemsLikeHTML(content:String) =
            content.length > 10 &&
            content.substring(0,10).toLowerCase().contains("<html>")
    }
}