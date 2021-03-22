package mite.core

import mite.ast.*
import mite.ihttp.InternalHttp.*
import java.util.concurrent.*
import mite.renderers.HtmlRenderer

/**
 * Object browser.
 */
object Objects : AbstractAstNodeHandler("/object") {

    private val objects = ConcurrentLinkedQueue<SingleObject>()

    init {
        MemoryGuard(objects)
    }

    data class SingleObject(val o : Any?) {
        fun toHtml(): List<String> =
            if (o==null) listOf("","","")
            else listOf(o.javaClass.simpleName,o.hashCode().toString(),o.toString())
    }

    fun record(o:Any) {
        objects.add(SingleObject(o))
    }

    fun linkTo(o:Any) = "/object@${objects.indexOf(SingleObject(o))}"

    override fun root(request: InternalRequest) = ReflectiveNode(objects.toList())

}