package mite.core

import java.io.*
import java.util.*

/**
 * HTTP version.
 */
data class HTTPVersion private constructor(val version: String, val mimeAware: Boolean) {

    @Throws(IOException::class)
    fun writeHeaders(writer: Writer, status: StatusCode, length: Int, contentType: ContentType) {
        if (mimeAware) {
            writeMIMEHeader(writer,status,length,contentType)
        }
    }

    @Throws(IOException::class)
    private fun writeMIMEHeader(writer: Writer, status: StatusCode, length: Int, contentType: ContentType) {
        writeln("HTTP 1.0 $status", writer)
        val now = Date()
        writeln("Date: $now", writer)
        writeln("Server: " + MiteHTTPServer.NAME, writer)
        writeln("Content-length: $length", writer)
        writeln("Content-type: ${contentType.streamName}", writer)
        writeln("", writer)
    }

    @Throws(IOException::class)
    private fun writeln(string: String, out: Writer) {
        out.write(string + "\r\n")
    }

    override fun toString(): String {
        return version
    }

    companion object {
        private const val UNKNOWN = "Unknown"
        val Unknown = HTTPVersion(UNKNOWN, false)
        fun fromRequest(request: String): HTTPVersion {
            val versionString = versionString(request)
            return HTTPVersion(versionString, versionString.startsWith("HTTP"))
        }

        private fun versionString(request: String): String {
            val st = StringTokenizer(request)
            st.nextToken() // we don't care about the method
            st.nextToken() // or what is being requested
            return if (st.hasMoreTokens()) {
                st.nextToken()
            } else UNKNOWN
        }
    }
}