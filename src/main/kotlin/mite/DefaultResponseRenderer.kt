package mite

import mite.http.HTTP.*
import mite.renderers.*

object DefaultResponseRenderer : Response.UnconditionalRenderer() {

    val composite = CompositeResponseRenderer()

    override fun render(request: Request, response: InternalResponse): Response {
        return ToStringRenderer.render(request,response)
    }

}