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
    private val reader = RequestReader

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
        val request = reader.readRequest(input)
        log(request)
        if (handler.handles(request)) {
            handler.handle(request, connection, out)
            return
        }
        throw UnsupportedOperationException(request.toString())
    }

    companion object {
        private fun log(t: Throwable) {
            Log.log(t)
        }

        private fun log(message: Array<String>) {
            Log.log("RequestProcessor : $message")
        }
    }

}