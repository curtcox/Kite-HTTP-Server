package mite.core

import java.io.IOException
import java.net.ServerSocket
import java.util.concurrent.*

/**
 * Opens a server socket and hands off any requests to another thread.
 */
class MiteHTTPServer(port: Int, handler: SocketRequestHandler) : Thread() {
    private val server: ServerSocket = ServerSocket(port)
    private val handler: SocketRequestHandler = handler
    private val executor: Executor = Executors.newFixedThreadPool(3)

    override fun run() {
        while (true) {
            try {
                executor.execute(RequestProcessor(server.accept(), handler))
            } catch (e: IOException) {
                log(e)
            }
        }
    }

    companion object {
        const val NAME = "KiteHTTPServer 0.1"
        @Throws(IOException::class)
        fun startListeningOnPort(port: Int, handler: HTTPHandler) {
            log("Accepting connections on port $port")
            val server = MiteHTTPServer(port, SocketRequestHandler.of(handler))
            server.start()
        }

        private fun log(t: Throwable) {
            Log.log(t)
        }

        private fun log(message: String) {
            Log.log(message)
        }
    }

}