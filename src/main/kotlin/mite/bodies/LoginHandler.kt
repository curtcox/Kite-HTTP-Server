package mite.bodies

import mite.headers.LoginCookie
import mite.http.HTTP
import mite.util.HTML

class LoginHandler : HTTP.Handler, HTML {

    override fun handleHeaders(httpRequest: HTTP.Request, response: HTTP.Response) =
        LoginCookie.handleHeaders(httpRequest,response)

    override fun handles(request: HTTP.Request): Boolean = true

    override fun handle(request: HTTP.Request): HTTP.Response = HTTP.Response.OK(
html(body(
"""
Login Required
""")))

    fun isLoggedIn() : (HTTP.Request) -> Boolean = { it: HTTP.Request -> LoginCookie.isLoggedIn(it) }

}