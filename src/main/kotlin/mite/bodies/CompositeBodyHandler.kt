package mite.bodies

import java.io.IOException
import mite.http.HTTP.*
import mite.ihttp.InternalHttp.*

/**
 * Handler that defers to other handlers.
 * It uses the first handler that says it can handle the given request.
 */
class CompositeBodyHandler constructor(vararg handlers: BodyHandler) : BodyHandler {

    private val handlers = handlers as Array<BodyHandler>
    private val debug = DebugBodyHandler(*handlers)

    @Throws(IOException::class)
    override fun handle(request: InternalRequest): InternalResponse? {
        if (debug.handles(request)) {
            return debug.handle(request)
        } else {
            for (handler in handlers) {
                if (handler.handles(request)) {
                    return handler.handle(request)
                }
            }
        }
        return InternalResponse.noValidHandler
    }

    override fun handles(request: InternalRequest): Boolean {
        if (debug.handles(request)) {
            return true
        } else {
            for (handler in handlers) {
                if (handler.handles(request)) {
                    return true
                }
            }
        }
        return false
    }

}