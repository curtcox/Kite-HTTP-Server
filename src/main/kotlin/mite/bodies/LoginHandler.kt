package mite.bodies

import mite.core.*
import mite.headers.LoginCookie
import mite.util.HTML

class LoginHandler : HTTPHandler, HTML {

    override fun handleHeaders(httpRequest: HTTPRequest, response: HTTPResponse) =
        LoginCookie.handleHeaders(httpRequest,response)

    override fun handles(request: HTTPRequest): Boolean = true

    override fun handle(request: HTTPRequest): HTTPResponse = HTTPResponse.OK(
html(body(
"""
Login Required
""")))

    fun isLoggedIn() : (HTTPRequest) -> Boolean = { it: HTTPRequest -> LoginCookie.isLoggedIn(it) }

}