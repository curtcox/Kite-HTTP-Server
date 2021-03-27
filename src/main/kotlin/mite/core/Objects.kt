package mite.core

import mite.ast.*
import mite.ihttp.InternalHttp.*
import java.util.concurrent.*
import mite.renderers.HtmlRenderer

/**
 * Object browser.
 */
object Objects : AbstractAstNodeHandler("/object") {

    // TODO see Log
    private val objects = ConcurrentLinkedQueue<SingleObject>()

    init {
        MemoryGuard(objects)
    }

    data class SingleObject(val o : Any?) {
        override fun toString() = "$o"
    }

    fun record(o:Any) {
        objects.add(SingleObject(o))
    }

    private fun indexOf(o:Any) : Int {
       val wrapped = SingleObject(o)
       if (!objects.contains(wrapped)) {
           record(o)
       }
        return objects.indexOf(wrapped)
    }

    fun linkTo(o:Any) = "/object@${indexOf(o)}"

    override fun root(request: InternalRequest) = ReflectiveNode(objects.toList())

}