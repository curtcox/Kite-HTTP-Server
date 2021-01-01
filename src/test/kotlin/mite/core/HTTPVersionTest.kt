package mite.core

import mite.core.HTTPVersion
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

class HTTPVersionTest {

    @Test
    fun version_is_version_name_when_given() {
        assertEquals("HTTP/11", HTTPVersion.fromRequest("GET / HTTP/11").version)
        assertEquals("HTTP/1.1", HTTPVersion.fromRequest("GET /monkeys HTTP/1.1").version)
    }

    @Test
    fun mimeAware_is_true_is_when_version_is_given() {
        Assert.assertTrue(HTTPVersion.fromRequest("GET / HTTP/1").mimeAware)
        Assert.assertTrue(HTTPVersion.fromRequest("GET /filthy HTTP/1.7").mimeAware)
    }

    @Test
    fun version_is_unknown_when_not_given() {
        assertEquals("Unknown", HTTPVersion.fromRequest("GET /").version)
        assertEquals("Unknown", HTTPVersion.fromRequest("GET /moo").version)
    }

    @Test
    fun mimeAware_is_false_when_no_version_given() {
        Assert.assertFalse(HTTPVersion.fromRequest("GET /").mimeAware)
        Assert.assertFalse(HTTPVersion.fromRequest("GET /moo").mimeAware)
    }

    @Test
    fun toString_is_version_name() {
        assertEquals("HTTP/11", HTTPVersion.fromRequest("GET / HTTP/11").toString())
        assertEquals("HTTP/1.1", HTTPVersion.fromRequest("GET /monkeys HTTP/1.1").toString())
    }
}