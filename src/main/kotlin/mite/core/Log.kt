package mite.core

import mite.ast.*
import java.time.Instant
import java.util.concurrent.*
import kotlin.reflect.KClass

/**
 * Shared logging abstraction.
 */
object Log : AbstractAstNodeHandler("/log",Entry::class) {

    private val entries = ConcurrentLinkedQueue<Entry>()
    data class Entry constructor(val time: Instant, val logger:KClass<*>, val record:Any, val stack:Throwable)

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

    override fun root() = ReflectiveNode(entries.toList())

}