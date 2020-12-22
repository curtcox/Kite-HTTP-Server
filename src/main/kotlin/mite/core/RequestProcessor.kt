package mite.core

import java.io.*
import java.net.Socket

/**
 * Processes a simple HTTP request.
 * This class contains just enough logic to determine who to hand the request to.
 */
internal class RequestProcessor(
    private val connection: Socket, private val handler: SocketRequestHandler //This will really handle the request
) : Runnable {

    private val input: InputStream = connection.getInputStream()
    private val out = connection.getOutputStream()

    override fun run() {
        connection.use {
            try {
                handleRequest()
            } catch (e: IOException) {
                log(e)
            }
        }
    }

    @Throws(IOException::class)
    private fun handleRequest() {
        val request = readRequest()
        log(request)
        if (handler.handles(request)) {
            handler.handle(request, connection, out)
            return
        }
        throw UnsupportedOperationException(request)
    }

    @Throws(IOException::class)
    private fun readRequest(): String {
        val requestLine = StringBuilder()
        val max_bytes_in_request = 1024
        for (i in 0 until max_bytes_in_request) {
            val c = input.read()
            if (c == '\r'.toInt() || c == '\n'.toInt()) break
            requestLine.append(c.toChar())
        }
        return requestLine.toString()
    }

    companion object {
        private fun log(t: Throwable) {
            Log.log(t)
        }

        private fun log(message: String) {
            Log.log("RequestProcessor : $message")
        }
    }

}