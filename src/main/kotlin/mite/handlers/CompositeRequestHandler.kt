package mite.handlers

import mite.*
import java.io.IOException

/**
 * Handler that defers to other handlers.
 * It uses the first handler that says it can handle the given request.
 */
class CompositeRequestHandler private constructor(vararg handlers: HTTPRequestHandler) : HTTPRequestHandler {

    private val handlers = handlers as Array<HTTPRequestHandler>

    @Throws(IOException::class)
    override fun handle(request: HTTPRequest): HTTPResponse? {
        for (handler in handlers) {
            if (handler.handles(request)) {
                return handler.handle(request)
            }
        }
        return HTTPResponse.empty
    }

    override fun handles(request: HTTPRequest): Boolean {
        for (handler in handlers) {
            if (handler.handles(request)) {
                return true
            }
        }
        return false
    }

    companion object {
        fun of(vararg handlers: HTTPRequestHandler): CompositeRequestHandler {
            return CompositeRequestHandler(*handlers)
        }
    }

}