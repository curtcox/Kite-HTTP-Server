package mite.bodies

import mite.http.HTTP.*
import mite.util.HTML

/**
 * To report to the client that the request is unauthorized.
 * Although the HTTP standard specifies "unauthorized", semantically this response means "unauthenticated".
 * That is, the client must authenticate itself to get the requested response.
 */
object UnauthorizedBodyHandler : BodyHandler, HTML {

    override fun handle(request: Request) = Response.of(UNAUTHORIZED_PAGE, ContentType.HTML, StatusCode.UNAUTHORIZED)

    override fun handles(request: Request) = true

    private val UNAUTHORIZED_PAGE =
        html(
            """
${head(title("Unauthorized"))}
${body(h1("HTTP Error 401: Unauthorized"))}
"""
            )
}