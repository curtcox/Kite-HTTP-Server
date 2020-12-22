package mite.core

import java.lang.Exception
import java.util.*

/**
 * The HTTP request sent to the server.
 * valid characters are ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~:/?#[]@!$&'()*+,;=
 */
data class HTTPRequest private constructor(
    val      string: String, // The entire unparsed request string we were sent
    val      method: String, // GET, POST, etc...
    val    filename: String, // http://server/filename
    val httpVersion: HTTPVersion
) {
    companion object {
        fun parse(string: String): HTTPRequest {
            return try {
                val tokenizer = StringTokenizer(string)
                val method = tokenizer.nextToken()
                val filename = tokenizer.nextToken()
                HTTPRequest(string, method, filename, HTTPVersion.fromRequest(string))
            } catch (e: Exception) {
                HTTPRequest(string, "", "", HTTPVersion.Unknown)
            }
        }
    }

}