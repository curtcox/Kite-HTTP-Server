package mite.bodies

import mite.core.*
import mite.util.HTML

/**
 * To report to the client that the request is unauthorized.
 * Although the HTTP standard specifies "unauthorized", semantically this response means "unauthenticated".
 * That is, the client must authenticate itself to get the requested response.
 */
class UnauthorizedBodyHandler private constructor() : HTTPBodyHandler {

    override fun handle(request: HTTPRequest) = HTTPResponse.of(UNAUTHORIZED_PAGE, StatusCode.UNAUTHORIZED)

    override fun handles(request: HTTPRequest) = true

    companion object : HTML {
        fun of(): UnauthorizedBodyHandler = UnauthorizedBodyHandler()

        private val UNAUTHORIZED_PAGE =
            html(
                """
${head(title("Unauthorized"))}
${body(h1("HTTP Error 401: Unauthorized"))}
"""
            )
    }
}