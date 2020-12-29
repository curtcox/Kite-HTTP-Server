package mite.core

import mite.bodies.AbstractBodyHandler
import java.util.concurrent.*

/**
 * Shared logging abstraction.
 */
object Log : AbstractBodyHandler("/log") {

    private val entries = ConcurrentLinkedQueue<Entry>()
    data class Entry constructor(val message:String,val throwable:Throwable)

    fun log(t: Throwable) {
        t.printStackTrace()
    }

    fun debug(t: Throwable) {
        t.printStackTrace()
    }

    fun log(message: String) {
        println(message)
    }

    override fun handle(request: HTTPRequest): HTTPResponse? {
        TODO("Not yet implemented")
    }
}