package mite.core

import mite.ast.Node
import mite.bodies.AbstractBodyHandler
import java.util.concurrent.*
import mite.http.HTTP.*
import mite.renderers.HtmlRenderer

/**
 * Object browser.
 */
object Objects : AbstractBodyHandler("/object") {

    private val objects = ConcurrentLinkedQueue<SingleObject>()
    val renderer = HtmlRenderer(object: Node.Renderer {
        override fun header() : Array<Any> = arrayOf("Class","Id","String")
        override fun render(node: Node) = (node.leaf as SingleObject).toHtml()
    })

    data class SingleObject(val o : Any?) {
        fun toHtml(): Array<Any> =
            if (o==null) arrayOf("","","")
            else arrayOf(o.javaClass.simpleName,o.hashCode(),o)
    }

    fun record(o:Any) {
        objects.add(SingleObject(o))
    }

    override fun handle(request: Request): InternalResponse {
        val id = hashCode(request)
        return if (id==null) {
            allObjects()
        } else {
            singleObject(objectFrom(id))
        }
    }

    private fun hashCode(request: Request) : Int? {
        val parts = request.filename.split("/")
        return if (parts.size == 3) parts[2].toInt() else null
    }

    private fun objectFrom(hash:Int) : Any? {
        for (o in objects) {
            if (o.hashCode()==hash)
                return o.o
        }
        return null
    }

    private fun allObjects()         = response(Node.list(Objects::class,objects.toList()))
    private fun singleObject(o:Any?) = response(Node.leaf(Objects::class,SingleObject(o)))
    private fun response(node:Node)  = InternalResponse.node(node, renderer)

}