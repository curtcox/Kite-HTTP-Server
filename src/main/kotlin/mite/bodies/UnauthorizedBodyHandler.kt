package mite.bodies

import mite.http.HTTP.*
import mite.ihttp.InternalHttp.*
/**
 * To report to the client that the request is unauthorized.
 * Although the HTTP standard specifies "unauthorized", semantically this response means "unauthenticated".
 * That is, the client must authenticate itself to get the requested response.
 */
object UnauthorizedBodyHandler : BodyHandler {

    override fun handle(request: InternalRequest) = InternalResponse.message("Unauthorized", StatusCode.UNAUTHORIZED)

    override fun handles(request: InternalRequest) = true

}