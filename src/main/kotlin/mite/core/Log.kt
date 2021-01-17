package mite.core

import mite.ast.Node
import mite.bodies.AbstractBodyHandler
import mite.util.HTML
import mite.http.HTTP.*
import java.time.Instant
import java.util.concurrent.*

/**
 * Shared logging abstraction.
 */
object Log : AbstractBodyHandler("/log"), HTML {

    private val entries = ConcurrentLinkedQueue<Entry>()
    data class Entry constructor(val time: Instant, val logger:Any, val record:Any, val stack:Throwable)

    private fun record(entry:Entry) {
        entries.add(entry)
        Objects.record(entry.record)
        Objects.record(entry.stack)
    }

    fun log(logger:Any, t: Throwable) {
        record(Entry(Instant.now(),t.message!!,t,t))
        t.printStackTrace()
    }

    fun debug(logger:Any, t: Throwable) {
        record(Entry(Instant.now(),t.message!!,t,t))
        t.printStackTrace()
    }

    fun log(logger:Any, record: Any) {
        record(Entry(Instant.now(),logger, record, Throwable()))
        println(record)
    }

    override fun handle(request: Request): InternalResponse {
        return InternalResponse.node(Node.list(Log::class,entries.toList()))
    }

//    private fun table(): String {
//        return """
//            <TABLE>
//            <TR><TH>Time</TH><TH>Message</TH><TH>Stack</TH></TR>
//            ${rows()}
//            </TABLE>
//        """.trimIndent()
//    }
//
//    private fun rows(): String {
//        val rows = StringBuilder()
//        for (entry in entries) {
//            rows.append("<TR><TD>${entry.time}</TD><TD>${entry.message}</TD><TD>${entry.stack}</TD></TR>")
//        }
//        return rows.toString()
//    }
}