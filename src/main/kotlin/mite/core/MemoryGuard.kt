package mite.core

import java.util.concurrent.ConcurrentLinkedQueue
import java.lang.management.ManagementFactory
import javax.management.*


data class MemoryGuard(val queue: ConcurrentLinkedQueue<*>) : NotificationListener {

    init {
        start()
    }

    private fun start() {
        val mbean = ManagementFactory.getMemoryMXBean()
        val emitter = mbean as NotificationEmitter
        emitter.addNotificationListener(this, null, null)
    }

    override fun handleNotification(notification: Notification?, handback: Any?) {
        println("$notification")
    }
}