package mite

import java.io.IOException
import java.net.ServerSocket
import java.util.concurrent.*

/**
 * Opens a server socket and hands off any requests to another thread.
 */
class MiteHTTPServer(port: Int, handler: SocketRequestHandler) : Thread() {
    private val server: ServerSocket
    private val handler: SocketRequestHandler
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
        const val NAME = "MiteHTTPServer 0.1"
        @Throws(IOException::class)
        fun startListeningOnPort(port: Int, http: HTTPRequestHandler) {
            log("Accepting connections on port $port")
            val server = MiteHTTPServer(port, SocketRequestHandler.of(http))
            server.start()
        }

        private fun log(t: Throwable) {
            t.printStackTrace()
        }

        private fun log(message: String) {
            println("MiteHTTPServer : $message")
        }
    }

    init {
        server = ServerSocket(port)
        this.handler = handler
    }
}