package mite.renderers

import mite.http.HTTP.Response.*
import java.lang.IllegalArgumentException
import mite.ihttp.InternalHttp.*
import mite.ihttp.InternalHttp.InternalResponse.*

/**
 * Renderer that defers to other renderers.
 * It uses the first renderer that says it can handle the given request.
 */
class CompositeResponseRenderer constructor(vararg renderers: Renderer) : Renderer {

    private val renderers = renderers as Array<Renderer>
    override fun render(request: InternalRequest, response: InternalResponse): Body {
        for (handler in renderers) {
            if (handler.handles(request,response)) {
                return handler.render(request,response)
            }
        }
        throw IllegalArgumentException()
    }

    override fun handles(request: InternalRequest, response: InternalResponse): Boolean {
        for (renderer in renderers) {
            if (renderer.handles(request,response)) {
                return true
            }
        }
        return false
    }

}