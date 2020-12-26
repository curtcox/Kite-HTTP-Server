package mite.bodies

import mite.core.*
import mite.headers.LoginCookie
import java.io.Writer

class LoginHandler : HTTPHandler {

    override fun writeHeaders(httpRequest: HTTPRequest, response: HTTPResponse, writer: Writer) {
        LoginCookie.writeHeaders(httpRequest,response,writer)
    }

    override fun handles(request: HTTPRequest): Boolean = true

    override fun handle(request: HTTPRequest): HTTPResponse = HTTPResponse.of(
"""
<HTML>
  <BODY>
  Login Required
  </BODY>
</HTML>    
""", StatusCode.OK)

    fun isLoggedIn() : (HTTPRequest) -> Boolean = { it: HTTPRequest -> LoginCookie.isLoggedIn(it) }

}