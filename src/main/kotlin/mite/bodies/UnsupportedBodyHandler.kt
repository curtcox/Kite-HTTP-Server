package mite.bodies

import mite.http.HTTP.*
import mite.util.HTML

/**
 * To report to the client that the request is unsupported.
 */
object UnsupportedBodyHandler : BodyHandler, HTML {

    override fun handle(request: Request) = InternalResponse.message("Not Implemented", StatusCode.NOT_IMPLEMENTED)

    override fun handles(request: Request) = true

}