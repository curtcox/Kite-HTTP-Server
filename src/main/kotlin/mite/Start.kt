package mite

import mite.bodies.*
import mite.core.*
import mite.handlers.*
import mite.headers.*
import java.io.IOException

/**
 * Configure and start the server.
 */
object Start {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val headers = CompositeHeaderWriter.of(
            ContentTypeHeaderWriter())
        val login = LoginHandler()
        val loggedIn = HandlerFromHeaderAndBody(
                headers,
                CompositeBodyHandler.of(
                    PreferencesRequestHandler.of(),
                    Log,
                    ProcessRequestHandler.of(),
                    EchoRequestHandler.of(),
                    UnsupportedBodyHandler.of()
                )
            )
        MiteHTTPServer.startListeningOnPort(
            8000, SwitchHandler(loggedIn,login,login.isLoggedIn())
        )
    }
}