package mite.core

import mite.ast.*
import mite.ihttp.InternalHttp.*
import java.time.Instant
import java.util.concurrent.*
import kotlin.reflect.KClass

/**
 * Shared logging abstraction.
 */
object Log : AbstractAstNodeHandler("/log") {

    val entries = ConcurrentLinkedQueue<Entry>()

    init {
        MemoryGuard(entries)
    }

    data class Entry constructor(val number:Int, val time: Instant, val logger:KClass<*>, val record:Any, val stack:Throwable) {
        constructor(info:ExchangeInfo, logger:KClass<*>, record:Any, stack:Throwable) : this(info.number,info.time,logger,record,stack)
    }

    private fun record(entry:Entry) {
        entries.add(entry)
        Objects.record(entry.record)
        Objects.record(entry.stack)
    }

    fun log(logger:KClass<*>, t: Throwable) {
        record(Entry(info(),logger,message(t),t))
        t.printStackTrace()
    }

    fun debug(logger: KClass<*>, t: Throwable) {
        record(Entry(info(),logger,message(t),t))
        t.printStackTrace()
    }

    fun message(t:Throwable) = t.message ?: ""

    fun log(logger:KClass<*>, record: Any) {
        record(Entry(info(),logger, record, Throwable()))
        printout(record)
    }

    fun log(logger:KClass<*>, record: Any,throwable: Throwable) {
        record(Entry(info(),logger, record, throwable))
        printout(record)
    }

    private fun printout(record:Any) {
        println(limitedString(record))
    }

    private fun limitedString(v:Any) : String {
        val max = 150
        return if (v == null) {"null"
        } else {
            val s = v.toString()
            if (s.length < max) { s }
            else { s.substring(0,max) + "..." }
        }
    }

    private fun info() = ExchangeTracker.info()

    override fun root(request: InternalRequest) = ReflectiveNode(entries.toList())

}