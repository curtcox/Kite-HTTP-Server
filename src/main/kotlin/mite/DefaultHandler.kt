package mite

import mite.http.HTTP.*
import mite.ihttp.InternalHttp.*

object DefaultHandler : Handler {

    val internalHandler = DefaultInternalHandler
    val responseRenderer = DefaultResponseRenderer

    override fun handle(request: Request) :Response.Body {
        val inner = InternalRequest(request)
        return responseRenderer.render(inner,internalHandler.handle(inner)!!)
    }

}