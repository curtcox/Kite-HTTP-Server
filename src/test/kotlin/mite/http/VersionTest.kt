package mite.http

import mite.http.HTTP.*
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

class VersionTest {

    @Test
    fun `version is version name when given`() {
        assertEquals("HTTP/11", Version.fromRequest("GET / HTTP/11").version)
        assertEquals("HTTP/1.1", Version.fromRequest("GET /monkeys HTTP/1.1").version)
    }

    @Test
    fun `mimeAware is true is when version is given`() {
        Assert.assertTrue(Version.fromRequest("GET / HTTP/1").mimeAware)
        Assert.assertTrue(Version.fromRequest("GET /filthy HTTP/1.7").mimeAware)
    }

    @Test
    fun `version is unknown when not given`() {
        assertEquals("Unknown", Version.fromRequest("GET /").version)
        assertEquals("Unknown", Version.fromRequest("GET /moo").version)
    }

    @Test
    fun `mimeAware is false when no version given`() {
        Assert.assertFalse(Version.fromRequest("GET /").mimeAware)
        Assert.assertFalse(Version.fromRequest("GET /moo").mimeAware)
    }

    @Test
    fun `toString is version name`() {
        assertEquals("HTTP/11", Version.fromRequest("GET / HTTP/11").toString())
        assertEquals("HTTP/1.1", Version.fromRequest("GET /monkeys HTTP/1.1").toString())
    }
}