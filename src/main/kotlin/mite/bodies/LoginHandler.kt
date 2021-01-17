package mite.bodies

import mite.headers.LoginCookie
import mite.http.HTTP.*
import mite.util.HTML

class LoginHandler : InternalHandler, HTML {

    override fun handleHeaders(httpRequest: Request, response: Response) =
        LoginCookie.handleHeaders(httpRequest,response)

    override fun handles(request: Request): Boolean = true

    override fun handle(request: Request): InternalResponse =
        InternalResponse.message("Login Required",StatusCode.UNAUTHORIZED)

    fun isLoggedIn() : (Request) -> Boolean = { it: Request -> LoginCookie.isLoggedIn(it) }

}