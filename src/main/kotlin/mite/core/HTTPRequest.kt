package mite.core

import java.lang.Exception
import java.util.*

/**
 * The HTTP request sent to the server.
 * valid characters are ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~:/?#[]@!$&'()*+,;=
 */
data class HTTPRequest constructor(
    val         raw: Array<String>, // The entire unparsed request string we were sent
    val      method: String, // GET, POST, etc...
    val    filename: String, // http://server/filename
    val httpVersion: HTTPVersion
) {
    companion object {
        val GET = "GET"
        val POST = "POST"
        fun parse(raw: Array<String>): HTTPRequest {
            return try {
                val first = raw[0]
                val tokenizer = StringTokenizer(first)
                val method = tokenizer.nextToken()
                val filename = tokenizer.nextToken()
                HTTPRequest(raw, method, filename, HTTPVersion.fromRequest(first))
            } catch (e: Exception) {
                HTTPRequest(raw, "", "", HTTPVersion.Unknown)
            }
        }
    }

}