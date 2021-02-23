package mite.core

import mite.TestObjects
import mite.ast.*
import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*

class RequestTrackerTest {

    val tracker = RequestTracker
    class TestRunnable : Runnable {
        override fun run() {
            TODO("Not yet implemented")
        }
    }

    @Test
    fun `next returns a runnable that cane be used to run the one it is given`() {
        //tracker.next()
    }

    @Test
    fun `next returns expected request info`() {
        //tracker.next()
    }

}