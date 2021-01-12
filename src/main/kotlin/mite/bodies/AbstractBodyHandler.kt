package mite.bodies

import mite.core.*
import mite.http.HTTP

/**
 * Skeletal implementation of HTTPRequestHandler.
 *
 * Implementors need to supply an implementation of handle that returns a String
 * if the handler handles the request.
 */
abstract class AbstractBodyHandler constructor(open val prefix:String = "") : HTTP.BodyHandler {

    /**
     * Call handle, to see if we actually DO handle this request.
     */
    final override fun handles(request: HTTP.Request): Boolean {
        return try {
            request.filename.startsWith(prefix) && handle(request) != null
        } catch (t: Throwable) {
            Log.debug(t)
            false
        }
    }

}