package mite.http

import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*

class HTTPTest {

    @Test
    fun binary() {
        assertTrue(ContentType.GIF.binary)
        assertTrue(ContentType.ICON.binary)
        assertTrue(ContentType.JPEG.binary)
    }

    @Test
    fun `not binary`() {
        assertFalse(ContentType.TEXT.binary)
        assertFalse(ContentType.HTML.binary)
        assertFalse(ContentType.AST.binary)
    }

}