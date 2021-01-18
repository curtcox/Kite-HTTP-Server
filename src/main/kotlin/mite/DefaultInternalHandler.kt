package mite

import mite.bodies.*
import mite.core.*
import mite.http.HTTP.*
import mite.handlers.*
import mite.headers.*

/**
 * Configure and start the server.
 */
object DefaultInternalHandler : InternalHandler {

    private val headers = ContentTypeHeaderHandler

    private val login = LoginHandler()

    private val favicon = FaviconHandler

    private fun handler(vararg handlers: BodyHandler) =
        HandlerFromHeaderAndBody(headers, CompositeBodyHandler(*handlers))

    private val needsToLogin = handler(favicon,login)

    private val loggedIn = handler(
        favicon,
        PreferencesRequestHandler.of(),
        Log,
        Objects,
        ProcessRequestHandler.of(),
        EchoRequestHandler.handler,
        UnsupportedBodyHandler
    )

    private val switchHandler = SwitchHandler(loggedIn,needsToLogin,login.isLoggedIn())

    override fun handleHeaders(httpRequest: Request, response: Response) =
        headers.handleHeaders(httpRequest,response)

    override fun handles(request: Request): Boolean = switchHandler.handles(request)

    override fun handle(request: Request): InternalResponse? = switchHandler.handle(request)

}