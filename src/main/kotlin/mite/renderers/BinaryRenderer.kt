package mite.renderers

import mite.http.HTTP.Response.*
import mite.ihttp.InternalHttp.*
import mite.ihttp.InternalHttp.InternalResponse.*

object BinaryRenderer : Renderer {

    override fun handles(request: InternalRequest, response: InternalResponse) = response.contentType.binary

    override fun render(request: InternalRequest, response: InternalResponse) =
        Body(response.payload as ByteArray,response.contentType,response.status)

}