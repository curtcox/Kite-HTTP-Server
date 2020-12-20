package mite.handlers

import mite.*

/**
 * To report to the client that the request is unauthorized.
 * Although the HTTP standard specifies "unauthorized", semantically this response means "unauthenticated".
 * That is, the client must authenticate itself to get the requested response.
 */
class UnauthorizedRequestHandler private constructor() : HTTPRequestHandler {

    override fun handle(request: HTTPRequest) = HTTPResponse.of(UNAUTHORIZED_PAGE, StatusCode.UNAUTHORIZED)

    override fun handles(request: HTTPRequest) = true

    companion object {
        fun of(): UnauthorizedRequestHandler = UnauthorizedRequestHandler()

        private const val UNAUTHORIZED_PAGE =
"""
<HTML>
  <HEAD> <TITLE>Not Implemented</TITLE> </HEAD>
  <BODY> <H1>HTTP Error 401: Unauthorized</H1> </BODY>
</HTML>
"""
    }
}