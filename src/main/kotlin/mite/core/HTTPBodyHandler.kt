package mite.core

import java.io.IOException

/**
 * This interface is used to define what an HTTP Server does.
 *
 * The intent is to allow groups of handlers to be composed and treated as a single handler.
 * See CompositeRequestHandler for a simple example of this concept.
 * Note that the expectation is subtly different for the root HTTPRequestHandler and component
 * HTTPHandlerS because the root has nothing to delegate to if it doesn't produce a response.
 *
 * Implementors may want to use AbstractRequestHandler, so that they only need implement handle.
 */
interface HTTPBodyHandler {

    /**
     * Return true if this handler handles this request.
     *
     * This method is primarily an optimization, since there is often no reasonable way to
     * determine beforehand if a method will throw an exception.
     */
    fun handles(request: HTTPRequest): Boolean

    /**
     * Handle this request and produce a response.
     *
     * Note that this method may well be called again from a different
     * thread before it returns.  It is the responsibility of the implementer to
     * ensure that that doesn't cause any problems.
     */
    @Throws(IOException::class)
    fun handle(request: HTTPRequest): HTTPResponse?
}