package mite.core

import mite.http.HTTP.*
import java.io.*
import java.net.Socket

/**
 * Processes a simple HTTP request.
 * This class contains just enough logic to determine who to hand the request to.
 */
internal class RequestProcessor(
    private val connection: Socket,
    private val handler: SocketRequestHandler //This will really handle the request
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
        //System.out.println("Handling $connection with $handler")
        val request = reader.readRequest(input)
        log(request)
        handler.handle(request, connection, out)
    }

    companion object {
        private fun log(t: Throwable) {
            Log.log(RequestProcessor::class,t)
        }

        private fun log(message: Request.Raw) {
            Log.log(RequestProcessor::class,message)
        }
    }

}