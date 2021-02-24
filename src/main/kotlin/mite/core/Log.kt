package mite.core

import mite.ast.*
import java.time.Instant
import java.util.concurrent.*
import kotlin.reflect.KClass

/**
 * Shared logging abstraction.
 */
object Log : AbstractAstNodeHandler("/log") {

    private val entries = ConcurrentLinkedQueue<Entry>()

    data class Entry constructor(val number:Int, val time: Instant, val logger:KClass<*>, val record:Any, val stack:Throwable) {
        constructor(info:ExchangeInfo, logger:KClass<*>, record:Any, stack:Throwable) : this(info.number,info.time,logger,record,stack)
    }

    private fun record(entry:Entry) {
        entries.add(entry)
        Objects.record(entry.record)
        Objects.record(entry.stack)
    }

    fun log(logger:KClass<*>, t: Throwable) {
        record(Entry(info(),logger,t.message!!,t))
        t.printStackTrace()
    }

    fun debug(logger: KClass<*>, t: Throwable) {
        record(Entry(info(),logger,t.message!!,t))
        t.printStackTrace()
    }

    fun log(logger:KClass<*>, record: Any) {
        record(Entry(info(),logger, record, Throwable()))
        println(record)
    }

    fun log(logger:KClass<*>, record: Any,throwable: Throwable) {
        record(Entry(info(),logger, record, throwable))
        println(record)
    }

    private fun info() = ExchangeTracker.info()

    override fun root() = ReflectiveNode(entries.toList())

}