package mite.handlers

import mite.*

/**
 * A handler that caches the results from another handler.
 * This class is motivated to avoid duplicate execution of expensive or destructive handlers
 * that lack a reliable independent handle implementation.
 * Namely, once to determine if they handle the request and once to actually do it.
 */
class CachedRequestHandler private constructor(handler: HTTPRequestHandler) : AbstractRequestHandler() {

    private val handler: HTTPRequestHandler = handler

    override fun handle(request: HTTPRequest): HTTPResponse? {
        return handler.handle(request)
    }

    companion object {
        fun of(handler: HTTPRequestHandler): CachedRequestHandler {
            return CachedRequestHandler(handler)
        }
    }
}