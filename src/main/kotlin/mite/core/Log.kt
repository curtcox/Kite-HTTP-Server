package mite.core

import mite.ast.Node
import mite.bodies.AbstractBodyHandler
import mite.http.HTTP.*
import mite.renderers.HtmlRenderer
import java.time.Instant
import java.util.concurrent.*

/**
 * Shared logging abstraction.
 */
object Log : AbstractBodyHandler("/log") {

    private val entries = ConcurrentLinkedQueue<Entry>()
    data class Entry constructor(val time: Instant, val logger:Any, val record:Any, val stack:Throwable) {
        fun toHtml() = "<TR><TD>${time}</TD><TD>${logger}</TD><TD>${record}</TD><TD>${stack}</TD></TR>"
    }

    val renderer = HtmlRenderer(object: Node.Renderer {
        override fun header() = "<TR><TH>Time</TH><TH>Log</TH><TH>Record</TH><TH>Stack</TH></TR>"
        override fun render(node: Node) = (node.leaf as Entry).toHtml()
    })

    private fun record(entry:Entry) {
        entries.add(entry)
        Objects.record(entry.record)
        Objects.record(entry.stack)
    }

    fun log(logger:Any, t: Throwable) {
        record(Entry(Instant.now(),logger,t.message!!,t))
        t.printStackTrace()
    }

    fun debug(logger:Any, t: Throwable) {
        record(Entry(Instant.now(),logger,t.message!!,t))
        t.printStackTrace()
    }

    fun log(logger:Any, record: Any) {
        record(Entry(Instant.now(),logger, record, Throwable()))
        println(record)
    }

    override fun handle(request: Request): InternalResponse {
        return InternalResponse.node(Node.list(Log::class,entries.toList()), renderer)
    }

}