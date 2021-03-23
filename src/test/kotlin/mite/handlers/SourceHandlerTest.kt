package mite.handlers

import mite.TestObjects
import mite.http.HTTP.*
import mite.payloads.Source
import org.junit.Test
import kotlin.test.*

class SourceHandlerTest {

    val sources = SourceHandler
    fun request(filename:String) = TestObjects.internalRequestForFilename(filename)

    @Test
    fun `ignores other prefixes`() {
        assertFalse(sources.handles(request("/horse/mite/Start.kt")))
    }

    @Test
    fun `returns source file`() {
        val response = sources.handle(request("/source/mite/Start.kt"))
        assertEquals(StatusCode.OK,response.status)
        assertTrue(response.payload is Source,"${response.payload}")

        val source = response.payload as Source
        assertEquals(listOf(
            """package mite""",
            """""",
            """import mite.core.*""",
            """import java.io.IOException""",
            """""",
            """/**""",
            """ * Configure and start the server.""",
            """ */""",
            """object Start {""",
            """""",
            """    @Throws(IOException::class)""",
            """    @JvmStatic""",
            """    fun main(args: Array<String>) {""",
            """        MiteHTTPServer.startListeningOnPort(8000, DefaultHandler)""",
            """    }""",
            """}"""
),
            source.lines)
    }
}