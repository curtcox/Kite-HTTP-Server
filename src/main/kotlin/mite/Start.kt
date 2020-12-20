package mite

import mite.handlers.*
import mite.headers.*
import java.io.IOException

object Start {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val auth : (HTTPRequest) -> Boolean = { request -> true }
        MiteHTTPServer.startListeningOnPort(
            8000,
            AuthorizationRequestHandler(
                CompositeRequestHandler.of(
                    ProcessRequestHandler.of(),
                    EchoRequestHandler.of(),
                    UnsupportedRequestHandler.of()
                ),
                auth
            )
            ,
            CompositeHeaderWriter.of(
            ContentTypeHeaderWriter())
        )
    }
}