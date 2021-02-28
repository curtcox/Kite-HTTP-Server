package mite.bodies

import mite.core.*
import mite.ihttp.InternalHttp.*
import mite.ihttp.InternalHttp.InternalRequest.*

/**
 * Skeletal implementation of HTTP.BodyHandler.
 *
 * Implementors need to supply an implementation of handle that returns a Response
 * if the handler handles the request.
 */
abstract class AbstractBodyHandler(open val filter:Filter) : BodyHandler {

    constructor(prefix:String = "") : this(object : Filter {
        override fun handles(request: InternalRequest): Boolean = request.filename.startsWith(prefix)
    })

    /**
     * Call handle, to see if we actually DO handle this request.
     */
     final override fun handles(request: InternalRequest): Boolean {
        return try {
//            println("/${filter.handles(request)}/${request.filename}/${this}")
            filter.handles(request) && handle(request) != null
        } catch (t: Throwable) {
            Log.debug(AbstractBodyHandler::class,t)
            false
        }
    }

}