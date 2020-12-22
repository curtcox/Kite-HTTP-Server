package mite

import mite.bodies.*
import mite.core.*
import mite.headers.*
import java.io.IOException

object Start {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val auth : (HTTPRequest) -> Boolean = { request -> true }
        MiteHTTPServer.startListeningOnPort(
            8000,
            AuthorizationBodyHandler(
                CompositeBodyHandler.of(
                    ProcessRequestHandler.of(),
                    EchoRequestHandler.of(),
                    UnsupportedBodyHandler.of()
                ),
                auth
            )
            ,
            CompositeHeaderWriter.of(
            ContentTypeHeaderWriter())
        )
    }
}