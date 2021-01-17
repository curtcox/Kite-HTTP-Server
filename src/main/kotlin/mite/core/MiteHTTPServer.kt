package mite.core

import java.io.IOException
import java.net.ServerSocket
import java.util.concurrent.*
import mite.http.HTTP.*

/**
 * Opens a server socket and hands off any requests to another thread.
 */
class MiteHTTPServer(private val server:ServerSocket, val handler: SocketRequestHandler) : Thread() {

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
        fun startListeningOnPort(port: Int, handler: Handler) {
            log("Accepting connections on port $port")
            val server = MiteHTTPServer(ServerSocketSupplier.port(port), SocketRequestHandler(handler))
            server.start()
        }

        private fun log(t: Throwable) {
            Log.log(MiteHTTPServer::class,t)
        }

        private fun log(message: String) {
            Log.log(MiteHTTPServer::class,message)
        }
    }

}