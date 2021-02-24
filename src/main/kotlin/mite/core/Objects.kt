package mite.core

import mite.ast.*
import mite.http.HTTP
import java.util.concurrent.*
import mite.renderers.HtmlRenderer

/**
 * Object browser.
 */
object Objects : AbstractAstNodeHandler("/object",HtmlRenderer(object: Node.Renderer {
    override fun header(list:List<*>) : List<String> = listOf("Class","Id","String")
    override fun render(node: Node) = (node.value as SingleObject).toHtml()
})) {

    private val objects = ConcurrentLinkedQueue<SingleObject>()

    data class SingleObject(val o : Any?) {
        fun toHtml(): List<String> =
            if (o==null) listOf("","","")
            else listOf(o.javaClass.simpleName,o.hashCode().toString(),o.toString())
    }

    fun record(o:Any) {
        objects.add(SingleObject(o))
    }

    override fun root(request: HTTP.Request) = ReflectiveNode(objects.toList())

}