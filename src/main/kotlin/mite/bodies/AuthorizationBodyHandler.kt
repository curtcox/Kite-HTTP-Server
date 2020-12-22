package mite.bodies

import mite.core.*

/**
 * Allows access to another handler only when authorized.
 */
class AuthorizationBodyHandler
    constructor(handler: HTTPBodyHandler, authorized: (HTTPRequest)->Boolean): HTTPBodyHandler
{

    private val handler = handler
    private val authorized = authorized
    private val unauthorized = UnauthorizedBodyHandler.of()

    override fun handles(request: HTTPRequest) =
        if (unauthorized(request)) true else handler.handles(request)

    override fun handle(request: HTTPRequest) =
        if (unauthorized(request)) unauthorized.handle(request) else handler.handle(request)

    private fun unauthorized(request: HTTPRequest) = !authorized.invoke(request)

}