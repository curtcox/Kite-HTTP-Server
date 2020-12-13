package mite.handlers

import mite.*

/**
 * Skeletal implementation of HTTPRequestHandler.
 * Implementors need to supply an implementation of handle that returns a String
 * if the handler handles the request.
 */
abstract class AbstractRequestHandler : HTTPRequestHandler {

    override fun handles(request: HTTPRequest): Boolean {
        return try {
            handle(request) != null
        } catch (t: Throwable) {
            debug(t)
            false
        }
    }

    private fun debug(t: Throwable) {
        t.printStackTrace()
    }
}