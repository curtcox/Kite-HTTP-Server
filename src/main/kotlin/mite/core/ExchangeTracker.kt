package mite.core

import java.time.Instant

data class ExchangeInfo(val number:Int, val time: Instant, val thread: Thread)

/**
 * Tracks HTTP request/response exchange info.
 */
object ExchangeTracker {

    private var id = 0
    private val infos = ArrayList<ExchangeInfo>()

    /**
     * Return a runnable that will advance the info when run.
     */
    fun next(runnable: Runnable): Runnable {
        return object : Runnable {
            override fun run() {
                nextInfo()
                runnable.run()
            }
        }
    }

    /**
     * Return the current info.
     */
    fun info(): ExchangeInfo = synchronized(this) {
        infos.firstOrNull { i -> current(i) } ?: throw badThread()
    }

    /**
     * Advance the tracker to the next info.
     */
    fun nextInfo() {
        synchronized(this) {
            infos.removeIf { i -> current(i) }
            infos.add(ExchangeInfo(id++, Instant.now(), currentThread()))
        }
    }

    private fun current(info:ExchangeInfo) = info.thread == currentThread()

    private fun currentThread() = Thread.currentThread()

    private fun badThread() = IllegalThreadStateException("${currentThread()} is not in ${infos}")
}