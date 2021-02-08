package mite.core

import mite.ast.*
import java.util.concurrent.*
import mite.http.HTTP.*
import mite.renderers.HtmlRenderer

/**
 * Object browser.
 */
object Objects : AbstractAstNodeHandler("/object",HtmlRenderer(object: Node.Renderer {
    override fun header() : List<String> = listOf("Class","Id","String")
    override fun render(node: Node) = (node.leaf as SingleObject).toHtml()
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

    override fun node(request: Request) = Node.list(Objects::class,objects.toList())

}