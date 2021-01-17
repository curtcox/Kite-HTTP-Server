package mite.core

import mite.ast.Node
import mite.bodies.AbstractBodyHandler
import mite.util.HTML
import java.util.concurrent.*
import mite.http.HTTP.*

/**
 * Object browser.
 */
object Objects : AbstractBodyHandler("/object"), HTML {

    private val objects = ConcurrentLinkedQueue<Any>()

    data class SingleObject(val o : Any?)

    fun record(o:Any) {
        objects.add(o)
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

//    private fun objectNode(o : Any?): String {
//        if (o==null)
//            return "null"
//        else
//            return "${o.hashCode()} $o ${o.javaClass}"
//    }
//
//    private fun table(): String {
//        return """
//            <TABLE>
//            <TR><TH>Hash Code</TH><TH>String</TH><TH>Class</TH></TR>
//            ${rows()}
//            </TABLE>
//        """.trimIndent()
//    }
//
//    private fun rows(): String {
//        val rows = StringBuilder()
//        for (o in objects) {
//            rows.append("<TR><TD>${o.hashCode()}</TD><TD>${o}</TD><TD>${o.javaClass}</TD></TR>")
//        }
//        return rows.toString()
//    }
}