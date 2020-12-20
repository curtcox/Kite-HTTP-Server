package mite.handlers

import mite.*

/**
 * To report to the client that the request is unsupported.
 */
class UnsupportedRequestHandler private constructor() : HTTPRequestHandler {

    override fun handle(request: HTTPRequest) = HTTPResponse.of(NOT_IMPLEMENTED_PAGE, StatusCode.NOT_IMPLEMENTED)

    override fun handles(request: HTTPRequest) = true

    companion object {
        fun of(): UnsupportedRequestHandler = UnsupportedRequestHandler()

        private const val NOT_IMPLEMENTED_PAGE =
"""
<HTML>
  <HEAD> <TITLE>Not Implemented</TITLE> </HEAD>
  <BODY> <H1>HTTP Error 501: Not Implemented</H1> </BODY>
</HTML>
"""
    }
}