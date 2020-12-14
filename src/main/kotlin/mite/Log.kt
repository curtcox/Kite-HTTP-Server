package mite

/**
 * Shared logging abstraction.
 */
object Log {

    fun log(t: Throwable) {
        t.printStackTrace()
    }

    fun debug(t: Throwable) {
        t.printStackTrace()
    }

    fun log(message: String) {
        println(message)
    }
}