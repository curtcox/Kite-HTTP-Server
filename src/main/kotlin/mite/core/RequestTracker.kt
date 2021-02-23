package mite.core

import java.time.Instant

data class RequestInfo(val number:Int, val time: Instant, val thread: Thread)

/**
 * Tracks request info.
 */
object RequestTracker {

    private var id = 0
    private val infos = ArrayList<RequestInfo>()

    fun next(runnable: Runnable): Runnable {
        return object : Runnable {
            override fun run() {
                nextInfo()
                runnable.run()
            }
        }
    }

    fun info(): RequestInfo = synchronized(this) {
        val ct = Thread.currentThread()
        println("${ct} infos = $infos")
        infos.forEach { i-> println("${ct} == ${i.thread} ? ${ct==i.thread} == ${current(i)}") }
        infos.first { i -> current(i) }
    }

    fun nextInfo() {
        synchronized(this) {
            infos.removeIf { i -> current(i) }
            infos.add(RequestInfo(id++, Instant.now(), currentThread()))
        }
    }

    private fun current(info:RequestInfo) = info.thread == currentThread()

    private fun currentThread() = Thread.currentThread()
}