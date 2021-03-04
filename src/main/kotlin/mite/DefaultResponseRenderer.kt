package mite

import mite.http.HTTP.*
import mite.renderers.*
import mite.ihttp.InternalHttp.*

object DefaultResponseRenderer : InternalResponse.UnconditionalRenderer() {

//    val composite = CompositeResponseRenderer(BinaryRenderer)

    override fun render(request: InternalRequest, response: InternalResponse): Response.Body {
//        if (composite.handles(request,response)) {
//            composite.render(request,response)
//        } else
        return if (response.renderer!=null) {
            response.renderer.render(request,response)
        } else {
            ToStringRenderer.render(request,response)
        }
    }

}