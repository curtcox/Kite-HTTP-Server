package mite.bodies

import mite.core.*

/**
 * Skeletal implementation of HTTPRequestHandler.
 *
 * Implementors need to supply an implementation of handle that returns a String
 * if the handler handles the request.
 */
abstract class AbstractBodyHandler : HTTPBodyHandler {

    /**
     * Call handle, to see if we actually DO handle this request.
     */
    final override fun handles(request: HTTPRequest): Boolean {
        return try {
            handle(request) != null
        } catch (t: Throwable) {
            Log.debug(t)
            false
        }
    }

}