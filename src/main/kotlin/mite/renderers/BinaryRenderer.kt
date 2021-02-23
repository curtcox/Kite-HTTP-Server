package mite.renderers

import mite.http.HTTP.*
import mite.http.HTTP.Response.*
import mite.http.HTTP.Response.Body.*

object BinaryRenderer : Renderer {

    override fun handles(request: Request, response: InternalResponse) = response.contentType.binary

    override fun render(request: Request, response: InternalResponse) =
        Body(response.payload as ByteArray,response.contentType,response.status)

}