package mite.core

import java.io.*
import java.util.*

/**
 * HTTP version.
 */
data class HTTPVersion private constructor(val version: String, val mimeAware: Boolean) {

    @Throws(IOException::class)
    fun writeHeaders(headers: Array<HTTPHeader>, writer: Writer) {
        if (mimeAware) {
            for (header in headers) {
                writeln("${header.key} ${header.value}", writer)
            }
            writeln("",writer)
        }
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
        val _1_1 = HTTPVersion("HTTP/1.1", true)
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