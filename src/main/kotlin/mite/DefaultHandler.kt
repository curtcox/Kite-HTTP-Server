package mite

import mite.bodies.*
import mite.core.*
import mite.handlers.*
import mite.headers.*

/**
 * Configure and start the server.
 */
object DefaultHandler : HTTPHandler {

    private val headers = ContentTypeHeaderHandler

    private val login = LoginHandler()

    private val favicon = FaviconHandler

    private fun handler(vararg handlers: HTTPBodyHandler) =
        HandlerFromHeaderAndBody(headers, CompositeBodyHandler(*handlers))

    private val needsToLogin = handler(favicon,login)

    private val loggedIn = handler(
        favicon,
        PreferencesRequestHandler.of(),
        Log,
        Objects,
        ProcessRequestHandler.of(),
        EchoRequestHandler.of(),
        UnsupportedBodyHandler
    )

    private val switchHandler = SwitchHandler(loggedIn,needsToLogin,login.isLoggedIn())

    override fun handleHeaders(httpRequest: HTTPRequest, response: HTTPResponse) =
        headers.handleHeaders(httpRequest,response)

    override fun handles(request: HTTPRequest): Boolean = switchHandler.handles(request)

    override fun handle(request: HTTPRequest): HTTPResponse? = switchHandler.handle(request)

}