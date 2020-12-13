package mite

import java.lang.Exception
import java.util.*

class HTTPRequest private constructor(// The entire unparsed request string we were sent
    val string: String, // GET, POST, etc...
    val method: String?, // http://server/filename
    val filename: String?, httpVersion: HTTPVersion
) {
    // valid characters are ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~:/?#[]@!$&'()*+,;=
    val httpVersion: HTTPVersion

    companion object {
        fun parse(string: String): HTTPRequest {
            return try {
                val tokenizer = StringTokenizer(string)
                val method = tokenizer.nextToken()
                val filename = tokenizer.nextToken()
                HTTPRequest(string, method, filename, HTTPVersion.fromRequest(string))
            } catch (e: Exception) {
                HTTPRequest(string, null, null, HTTPVersion.Unknown)
            }
        }
    }

    init {
        this.httpVersion = httpVersion
    }
}