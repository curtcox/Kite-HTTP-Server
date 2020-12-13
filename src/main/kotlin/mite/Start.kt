package mite

import mite.handlers.CompositeRequestHandler
import mite.handlers.EchoRequestHandler
import mite.handlers.ProcessRequestHandler
import mite.handlers.ProcessRequestHandler.of
import mite.handlers.UnsupportedRequestHandler
import java.io.IOException

object Start {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        MiteHTTPServer.startListeningOnPort(
            8000,
            CompositeRequestHandler.of(
                of(),
                EchoRequestHandler.of(),
                UnsupportedRequestHandler.of()
            )
        )
    }
}