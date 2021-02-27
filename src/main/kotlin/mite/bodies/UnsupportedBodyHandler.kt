package mite.bodies

import mite.http.HTTP.*
import mite.html.HTML
import mite.ihttp.InternalHttp.*
/**
 * To report to the client that the request is unsupported.
 */
object UnsupportedBodyHandler : BodyHandler {

    override fun handle(request: Request) = InternalResponse.message("Not Implemented", StatusCode.NOT_IMPLEMENTED)

    override fun handles(request: Request) = true

}