package mite

import mite.bodies.*
import mite.core.*
import mite.handlers.*
import mite.headers.*
import java.io.IOException

object Start {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val auth : (HTTPRequest) -> Boolean = { request -> true }
        val loggedIn = HandlerFromHeaderAndBody(
                CompositeHeaderWriter.of(
                    ContentTypeHeaderWriter()),
                CompositeBodyHandler.of(
                    ProcessRequestHandler.of(),
                    EchoRequestHandler.of(),
                    UnsupportedBodyHandler.of()
                )
            )
        MiteHTTPServer.startListeningOnPort(
            8000, SwitchHandler(loggedIn,loggedIn,auth)
        )
    }
}