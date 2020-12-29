package mite.bodies

import mite.core.*
import mite.util.HTML

/**
 * To report to the client that the request is unsupported.
 */
class UnsupportedBodyHandler private constructor() : HTTPBodyHandler {

    override fun handle(request: HTTPRequest) = HTTPResponse.of(NOT_IMPLEMENTED_PAGE, StatusCode.NOT_IMPLEMENTED)

    override fun handles(request: HTTPRequest) = true

    companion object : HTML {
        fun of(): UnsupportedBodyHandler = UnsupportedBodyHandler()

        private val NOT_IMPLEMENTED_PAGE =
html(
"""
${head(title("Not Implemented"))}
${body(h1("HTTP Error 501: Not Implemented"))}
"""
)
    }
}