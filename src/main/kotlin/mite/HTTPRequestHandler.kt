package mite

import java.io.IOException

/**
 * This interface is used to define what a HTTP Server does.
 * Implementors may want to use AbstractRequestHandler, so that they only need implement handle.
 */
interface HTTPRequestHandler {
    /**
     * Return true if this handler handles this request.
     */
    fun handles(request: HTTPRequest): Boolean

    /**
     * Handle this request.
     * Note that this method may well be called again from a different
     * thread before it returns.  It is the responsibility of the implementer to
     * ensure that that doesn't cause any problems.
     */
    @Throws(IOException::class)
    fun handle(request: HTTPRequest): HTTPResponse
}