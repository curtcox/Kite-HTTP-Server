package mite

import mite.bodies.*
import mite.core.*
import mite.handlers.*
import mite.ihttp.InternalHttp.*

/**
 * Configure and start the server.
 */
object DefaultInternalHandler : BodyHandler {

    private val login = LoginHandler()

    private val favicon = ResourceHandler

    private fun handler(vararg handlers: BodyHandler) = CompositeBodyHandler(*handlers)

    private val needsToLogin = handler(favicon,login)

    private val loggedIn = handler(
        favicon,
        PreferencesRequestHandler.of(),
        Log,
        Objects,
        ProcessRequestHandler.of(),
        EchoRequestHandler,
        UnsupportedBodyHandler
    )

    private val switchHandler = SwitchHandler(loggedIn,needsToLogin,login.isLoggedIn())

    override fun handles(request: InternalRequest): Boolean = switchHandler.handles(request)

    override fun handle(request: InternalRequest): InternalResponse? = switchHandler.handle(request)

}