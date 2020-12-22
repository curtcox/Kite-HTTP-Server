package mite.bodies

import mite.core.*

/**
 * To report to the client that the request is unsupported.
 */
class UnsupportedBodyHandler private constructor() : HTTPBodyHandler {

    override fun handle(request: HTTPRequest) = HTTPResponse.of(NOT_IMPLEMENTED_PAGE, StatusCode.NOT_IMPLEMENTED)

    override fun handles(request: HTTPRequest) = true

    companion object {
        fun of(): UnsupportedBodyHandler = UnsupportedBodyHandler()

        private const val NOT_IMPLEMENTED_PAGE =
"""
<HTML>
  <HEAD> <TITLE>Not Implemented</TITLE> </HEAD>
  <BODY> <H1>HTTP Error 501: Not Implemented</H1> </BODY>
</HTML>
"""
    }
}