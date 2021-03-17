package mite

import mite.http.HTTP.*
import mite.renderers.*
import mite.ihttp.InternalHttp.*

object DefaultResponseRenderer : InternalResponse.UnconditionalRenderer() {

    override fun render(request: InternalRequest, response: InternalResponse): Response.Body {
        return if (response.renderer!=null) {
            response.renderer.render(request,response)
        } else {
            ToStringRenderer.render(request,response)
        }
    }

}