package mite.bodies

import mite.core.*
import mite.core.HTTP.*

/**
 * A handler that caches the results from another handler.
 * This class is motivated to avoid duplicate execution of expensive or destructive handlers
 * that lack a reliable independent handle implementation.
 * Namely, once to determine if they handle the request and once to actually do it.
 */
class CachedBodyHandler private constructor(handler: BodyHandler) : AbstractBodyHandler() {

    private val handler = handler
    private val cache: MutableMap<Request, Response?> = mutableMapOf()

    override fun handle(request: Request): Response? {
        return if (cache.containsKey(request)) cache.get(request)
        else {
            val response = handler.handle(request)
            cache.put(request,response)
            response
        }
    }

    companion object {
        fun of(handler: BodyHandler): CachedBodyHandler {
            return CachedBodyHandler(handler)
        }
    }
}