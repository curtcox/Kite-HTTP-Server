package mite.core

import mite.bodies.AbstractBodyHandler
import mite.util.HTML
import java.time.Instant
import java.util.concurrent.*

/**
 * Shared logging abstraction.
 */
object Log : AbstractBodyHandler("/log"), HTML {

    private val entries = ConcurrentLinkedQueue<Entry>()
    data class Entry constructor(val time: Instant, val message:String, val stack:Throwable)

    fun log(t: Throwable) {
        entries.add(Entry(Instant.now(),t.message!!,t))
        t.printStackTrace()
    }

    fun debug(t: Throwable) {
        entries.add(Entry(Instant.now(),t.message!!,t))
        t.printStackTrace()
    }

    fun log(message: String) {
        entries.add(Entry(Instant.now(),message!!,Throwable()))
        println(message)
    }

    override fun handle(request: HTTPRequest): HTTPResponse {
        return HTTPResponse.of(html(body(table())),StatusCode.OK)
    }

    private fun table(): String {
        val rows = StringBuilder()
        for (entry in entries) {
            rows.append("<TR><TD>${entry.time}</TD><TD>${entry.message}</TD><TD>${entry.stack}</TD></TR>")
        }
        val table = """
            <TABLE>
            <TR><TH>Time</TH><TH>Message</TH><TH>Stack</TH></TR>
            ${rows()}
            </TABLE>
        """.trimIndent()
        return table
    }

    private fun rows(): String {
        val rows = StringBuilder()
        for (entry in entries) {
            rows.append("<TR><TD>${entry.time}</TD><TD>${entry.message}</TD><TD>${entry.stack}</TD></TR>")
        }
        return rows.toString()
    }
}