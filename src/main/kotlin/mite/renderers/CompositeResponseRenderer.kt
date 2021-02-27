package mite.renderers

import mite.http.HTTP.*
import mite.http.HTTP.Response.*
import mite.http.HTTP.Response.Body.*
import java.lang.IllegalArgumentException
import mite.ihttp.InternalHttp.*
/**
 * Renderer that defers to other renderers.
 * It uses the first renderer that says it can handle the given request.
 */
class CompositeResponseRenderer constructor(vararg renderers: Renderer) : Renderer {

    private val renderers = renderers as Array<Renderer>
    override fun render(request: Request, response: InternalResponse): Body {
        for (handler in renderers) {
            if (handler.handles(request,response)) {
                return handler.render(request,response)
            }
        }
        throw IllegalArgumentException()
    }

    override fun handles(request: Request, response: InternalResponse): Boolean {
        for (renderer in renderers) {
            if (renderer.handles(request,response)) {
                return true
            }
        }
        return false
    }

}