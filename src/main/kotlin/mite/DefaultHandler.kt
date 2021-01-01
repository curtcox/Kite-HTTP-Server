package mite

import mite.bodies.*
import mite.core.*
import mite.handlers.*
import mite.headers.*
import java.io.*

/**
 * Configure and start the server.
 */
object DefaultHandler : HTTPHandler {

    private val headers = CompositeHeaderWriter.of(ContentTypeHeaderWriter())

    private val login = LoginHandler()

    private val favicon = FaviconHandler

    private fun handler(vararg handlers: HTTPBodyHandler) =
        HandlerFromHeaderAndBody(headers, CompositeBodyHandler.of(*handlers))

    private val needsToLogin = handler(favicon,login)

    private val loggedIn = handler(
        favicon,
        PreferencesRequestHandler.of(),
        Log,
        Objects,
        ProcessRequestHandler.of(),
        EchoRequestHandler.of(),
        UnsupportedBodyHandler.of()
    )

    private val switchHandler = SwitchHandler(loggedIn,needsToLogin,login.isLoggedIn())

    override fun writeHeaders(httpRequest: HTTPRequest, response: HTTPResponse, writer: Writer) =
        headers.writeHeaders(httpRequest,response,writer)

    override fun handles(request: HTTPRequest): Boolean = switchHandler.handles(request)

    override fun handle(request: HTTPRequest): HTTPResponse? = switchHandler.handle(request)

}