package mite.bodies

import mite.core.*
import mite.headers.LoginCookie
import mite.util.HTML
import java.io.Writer

class LoginHandler : HTTPHandler, HTML {

    override fun writeHeaders(httpRequest: HTTPRequest, response: HTTPResponse, writer: Writer) {
        LoginCookie.writeHeaders(httpRequest,response,writer)
    }

    override fun handles(request: HTTPRequest): Boolean = true

    override fun handle(request: HTTPRequest): HTTPResponse = HTTPResponse.OK(
html(body(
"""
Login Required
""")))

    fun isLoggedIn() : (HTTPRequest) -> Boolean = { it: HTTPRequest -> LoginCookie.isLoggedIn(it) }

}