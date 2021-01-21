package mite.renderers

import mite.http.HTTP.*

object BinaryRenderer : Response.Renderer {

    override fun handles(request: Request, response: InternalResponse) = response.contentType.binary

    override fun render(request: Request, response: InternalResponse) =
        Response(response.payload as ByteArray,response.contentType,response.status)

}