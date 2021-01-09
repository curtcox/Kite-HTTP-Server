package mite.core

import java.io.*

/**
 * Processes a simple HTTP request.
 * This class contains just enough logic to determine who to hand the request to.
 */
internal object RequestReader {

    @Throws(IOException::class)
    fun readRequest(input: InputStream): Array<String> {
        val requestLine = StringBuilder()
        val max_bytes_in_request = 1024
        for (i in 0 until max_bytes_in_request) {
            val c = input.read()
            if (c == '\r'.toInt() || c == '\n'.toInt()) break
            requestLine.append(c.toChar())
        }
        return arrayOf(requestLine.toString())
    }

}