package mite.bodies

import mite.core.*
import mite.http.HTTP.*

/**
 * Skeletal implementation of HTTP.BodyHandler.
 *
 * Implementors need to supply an implementation of handle that returns a Response
 * if the handler handles the request.
 */
abstract class AbstractBodyHandler(open val filter:Request.Filter) : BodyHandler {

    constructor(prefix:String = "") : this(object : Request.Filter {
        override fun handles(request: Request): Boolean = request.filename.startsWith(prefix)
    })

    /**
     * Call handle, to see if we actually DO handle this request.
     */
     final override fun handles(request: Request): Boolean {
        return try {
            println("/${filter.handles(request)}/${request.filename}/${this}")
            filter.handles(request) && handle(request) != null
        } catch (t: Throwable) {
            Log.debug(AbstractBodyHandler::class,t)
            false
        }
    }

//    final override fun handle(request: Request): InternalResponse? {
//        TODO("Not yet implemented")
//    }

}