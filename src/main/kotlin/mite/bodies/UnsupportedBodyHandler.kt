package mite.bodies

import mite.http.HTTP.*
import mite.ihttp.InternalHttp.*

/**
 * To report to the client that the request is unsupported.
 */
object UnsupportedBodyHandler : BodyHandler {

    override fun handle(request: InternalRequest) = InternalResponse.message("Not Implemented", StatusCode.NOT_IMPLEMENTED)

    override fun handles(request: InternalRequest) = true

}