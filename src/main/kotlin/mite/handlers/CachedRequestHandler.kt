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
    private val cache: MutableMap<HTTPRequest,HTTPResponse?> = mutableMapOf()

    override fun handle(request: HTTPRequest): HTTPResponse? {
        return if (cache.containsKey(request)) cache.get(request)
        else {
            val response = handler.handle(request)
            cache.put(request,response)
            response
        }
    }

    companion object {
        fun of(handler: HTTPRequestHandler): CachedRequestHandler {
            return CachedRequestHandler(handler)
        }
    }
}