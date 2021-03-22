package mite.core

import org.junit.Test
import kotlin.test.*
import mite.html.HTML
import java.io.*
import java.net.Socket
import mite.http.HTTP.*

class SocketRequestHandlerTest {

    @Test
    fun `can create`() {
        val inner = object : Handler { override fun handle(request: Request): Response.Body = TODO() }
        val handler = SocketRequestHandler(inner)
        assertNotNull(handler)
    }

    private fun echo(status:StatusCode) = object : Handler {
        override fun handle(request: Request): Response.Body = Response.Body(HTML.Tags.string(request),status)
    }

    @Test
    fun `handle writes response without headers when version is not MIME`() {
        val request = Request.Raw(arrayOf("GET /over"))
        val stream = ByteArrayOutputStream()
        val handler = SocketRequestHandler(echo(StatusCode.OK))

        ExchangeTracker.nextInfo()
        handler.handle(request,Socket(),stream)

        val out = stream.toString()
        assertTrue(out.contains(request.toString()))
        assertFalse(out.contains(StatusCode.OK.toString()))
    }

    @Test
    fun `handle writes response with headers when version is MIME`() {
        val request = Request.Raw(arrayOf("GET /mime HTTP/1.0"))
        val stream = ByteArrayOutputStream()
        val handler = SocketRequestHandler(echo(StatusCode.OK))

        ExchangeTracker.nextInfo()
        handler.handle(request,Socket(),stream)

        val out = stream.toString()
        assertTrue(out.contains(request.toString()))
        assertTrue(out.contains(StatusCode.OK.toString()))
    }

}