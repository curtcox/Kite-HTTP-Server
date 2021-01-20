package mite.renderers

import mite.http.HTTP.*
import java.lang.IllegalArgumentException

/**
 * Renderer that defers to other renderers.
 * It uses the first renderer that says it can handle the given request.
 */
class CompositeResponseRenderer constructor(vararg renderers: Response.Renderer) : Response.Renderer {

    private val renderers = renderers as Array<Response.Renderer>
    override fun render(request: Request, response: InternalResponse): Response {
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