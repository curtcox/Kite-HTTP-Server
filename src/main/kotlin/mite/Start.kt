package mite

import mite.core.*
import java.io.IOException

/**
 * Configure and start the server.
 */
object Start {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        MiteHTTPServer.startListeningOnPort(8000, DefaultHandler)
    }
}