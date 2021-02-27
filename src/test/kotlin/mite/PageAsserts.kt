package mite

import kotlin.test.*

data class PageAsserts(val page:String) {

    fun startsWith(string:String) {
        assertTrue(page.startsWith(string),page)
    }

    fun endsWith(string:String) {
        assertTrue(page.endsWith(string),page)
    }

    fun contains(string:String) {
        assertTrue(page.contains(string),"$string not found in $page")
    }

    fun dump() {
        assertEquals("",page)
    }

}