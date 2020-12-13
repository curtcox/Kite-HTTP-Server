package mite.handlers

import mite.HTTPRequest
import mite.HTTPRequestHandler
import mite.HTTPResponse
import mite.StatusCode

/**
 * To report to the client that the request is unsupported.
 */
class UnsupportedRequestHandler private constructor() : HTTPRequestHandler {
    override fun handle(request: HTTPRequest): HTTPResponse {
        return HTTPResponse.of(NOT_IMPLEMENTED_PAGE, StatusCode.NOT_IMPLEMENTED)
    }

    override fun handles(request: HTTPRequest): Boolean {
        return true
    }

    companion object {
        fun of(): UnsupportedRequestHandler {
            return UnsupportedRequestHandler()
        }

        private const val NOT_IMPLEMENTED_PAGE = "<HTML>" +
                "<HEAD> <TITLE>Not Implemented</TITLE> </HEAD>" +
                "<BODY> <H1>HTTP Error 501: Not Implemented</H1> </BODY>" +
                "</HTML>"
    }
}