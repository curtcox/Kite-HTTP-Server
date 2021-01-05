package mite.bodies

import mite.core.*
import mite.util.HTML

/**
 * To report to the client that the request is unsupported.
 */
object UnsupportedBodyHandler : HTTPBodyHandler, HTML {

    override fun handle(request: HTTPRequest) = HTTPResponse.of(NOT_IMPLEMENTED_PAGE, ContentType.HTML, StatusCode.NOT_IMPLEMENTED)

    override fun handles(request: HTTPRequest) = true

    private val NOT_IMPLEMENTED_PAGE =
html(
"""
${head(title("Not Implemented"))}
${body(h1("HTTP Error 501: Not Implemented"))}
"""
)

}