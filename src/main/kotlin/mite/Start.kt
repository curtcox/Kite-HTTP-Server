package mite

import mite.handlers.*
import mite.headers.ContentTypeHeaderWriter
import java.io.IOException

object Start {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        MiteHTTPServer.startListeningOnPort(
            8000,
            CompositeRequestHandler.of(
                ProcessRequestHandler.of(),
                EchoRequestHandler.of(),
                UnsupportedRequestHandler.of()
            ),
            ContentTypeHeaderWriter()
        )
    }
}