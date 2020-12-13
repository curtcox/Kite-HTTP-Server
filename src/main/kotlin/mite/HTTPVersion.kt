package mite

import java.util.*

/**
 * HTTP version.
 */
class HTTPVersion private constructor(val version: String, val mimeAware: Boolean) {
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