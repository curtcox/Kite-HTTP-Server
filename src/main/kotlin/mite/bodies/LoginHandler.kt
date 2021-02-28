package mite.bodies

import mite.headers.LoginCookie
import mite.http.HTTP.*
import mite.ihttp.InternalHttp.*

class LoginHandler : BodyHandler {

    override fun handles(request: InternalRequest): Boolean = true

    override fun handle(request: InternalRequest): InternalResponse =
        InternalResponse.message("Login Required",StatusCode.UNAUTHORIZED)

    fun isLoggedIn() : (InternalRequest) -> Boolean = { it: InternalRequest -> LoginCookie.isLoggedIn(it) }

}