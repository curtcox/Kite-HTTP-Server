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
        override fun header() = "<TR><TH>Class</TH><TH>Id</TH><TH>String</TH></TR>"
        override fun render(node: Node): String {
            val entry = node.leaf as SingleObject
            return "<TR><TD>${entry.javaClass}</TD><TD>${entry.hashCode()}</TD><TD>${entry}</TD></TR>"
        }
    })

    data class SingleObject(val o : Any?)

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
                return o
        }
        return null
    }

    private fun allObjects()         = InternalResponse.node(Node.list(Objects::class,objects.toList()))
    private fun singleObject(o:Any?) = InternalResponse.node(Node.leaf(Objects::class,SingleObject(o)))

}