package mite.bodies

import java.io.IOException
import mite.http.HTTP.*

/**
 * Handler that defers to other handlers.
 * It uses the first handler that says it can handle the given request.
 */
class CompositeBodyHandler constructor(vararg handlers: BodyHandler) : BodyHandler {

    private val handlers = handlers as Array<BodyHandler>

    @Throws(IOException::class)
    override fun handle(request: Request): Response? {
        for (handler in handlers) {
            if (handler.handles(request)) {
                return handler.handle(request)
            }
        }
        return Response.empty
    }

    override fun handles(request: Request): Boolean {
        for (handler in handlers) {
            if (handler.handles(request)) {
                return true
            }
        }
        return false
    }

}