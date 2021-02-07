package mite.core

import mite.ast.*
import mite.bodies.AbstractBodyHandler
import mite.http.HTTP.*
import mite.renderers.HtmlRenderer
import java.time.Instant
import java.util.concurrent.*
import kotlin.reflect.KClass

/**
 * Shared logging abstraction.
 */
object Log : AbstractBodyHandler("/log") {

    private val entries = ConcurrentLinkedQueue<Entry>()
    data class Entry constructor(val time: Instant, val logger:KClass<*>, val record:Any, val stack:Throwable) {
    //    fun toHtml() = listOf(time.toString(),logger.simpleName.toString(),record.toString(),stack.toString())
    }

    val renderer = HtmlRenderer(ReflectionRenderer(Entry::class))
//    val renderer = HtmlRenderer(object: Node.Renderer {
//        override fun header() : List<String> = listOf("Time","Log","Record","Stack")
//        override fun render(node: Node) = (node.leaf as Entry).toHtml()
//    })

    private fun record(entry:Entry) {
        entries.add(entry)
        Objects.record(entry.record)
        Objects.record(entry.stack)
    }

    fun log(logger:KClass<*>, t: Throwable) {
        record(Entry(Instant.now(),logger,t.message!!,t))
        t.printStackTrace()
    }

    fun debug(logger: KClass<*>, t: Throwable) {
        record(Entry(Instant.now(),logger,t.message!!,t))
        t.printStackTrace()
    }

    fun log(logger:KClass<*>, record: Any) {
        record(Entry(Instant.now(),logger, record, Throwable()))
        println(record)
    }

    override fun handle(request: Request): InternalResponse {
        return InternalResponse.node(Node.list(Log::class,entries.toList()), renderer)
    }

}