package mite.handlers

import mite.*

/**
 * Allows access to another handler only when authorized.
 */
class AuthorizationRequestHandler
    constructor(handler: HTTPRequestHandler, authorized: (HTTPRequest)->Boolean): HTTPRequestHandler
{

    private val handler = handler
    private val authorized = authorized
    private val unauthorized = UnauthorizedRequestHandler.of()

    override fun handles(request: HTTPRequest) =
        if (unauthorized(request)) true else handler.handles(request)

    override fun handle(request: HTTPRequest) =
        if (unauthorized(request)) unauthorized.handle(request) else handler.handle(request)

    private fun unauthorized(request: HTTPRequest) = !authorized.invoke(request)

}