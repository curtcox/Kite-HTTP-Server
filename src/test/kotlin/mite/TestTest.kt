package mite

import org.junit.Test
import kotlin.test.*

class TestTest {

    @Test
    fun `true && true`() {
        val x = true && true
        assertEquals(true,x)
    }

    @Test
    fun `!true && !true`() {
        val x = !true && !true
        assertEquals(false,x)
    }

    @Test
    fun `!true !true`() {
        val x =
            !true
            !true
        assertEquals(false,x)
    }

    @Test
    fun `!false !true`() {
        val x =
            !false
            !true
        assertEquals(true,x)
    }

    @Test
    fun `false true`() {
        val x =
            false
            true
        assertEquals(false,x)
    }

    @Test
    fun `true false`() {
        val x =
            true
            false
        assertEquals(true,x)
    }

    @Test
    fun `true && true && true && false == false`() {
        val everythingIsTrue =
            ("You want this to be true" != "and it is") &&
            ("You want this to be true, too" != "and it is, too") &&
            ("The third thing you want to be true" != "yep, still true")
            ("But this isn't true" == "So, the whole thing must be false")

        assertEquals(true,everythingIsTrue)
    }

    fun everythingMustBeTrueForThisToBeTrue() =
        try {
            "This has to be true" == "but it isn't" &&
            "This has to be true, too " == "but it isn't either" &&
            "One more thing " == "that still isn't true"
            "Well this is true" != "but that hardly matters"
        } catch (t: Throwable) {
            false
        }

    @Test
    fun `false && false && false && true == TRUE?`() {
        assertEquals(true,everythingMustBeTrueForThisToBeTrue())
    }

}