package mite.core

import mite.TestObjects
import mite.ast.*
import mite.core.Log.Entry
import mite.http.HTTP.*
import org.junit.Test
import java.time.Instant
import kotlin.test.*
import mite.ihttp.InternalHttp.*

class LogTest {

    val renderer = Log.renderer

    fun request(filename:String) = TestObjects.internalRequestForFilename(filename)

    fun entries(entries:List<Entry>) = InternalResponse.node(SimpleNode.list(Log::class, entries))

    @Test
    fun `handles expected requests`() {
        assertTrue(Log.handles(request("/log")))
    }

    @Test
    fun `ignores other requests`() {
        assertFalse(Log.handles(request("/schmog")))
    }

    @Test
    fun `response is HTML`() {
        val response = Log.handle(request("/log"))
        assertEquals(ContentType.AST,response.contentType)
        assertEquals(StatusCode.OK,response.status)
        assertTrue(response.payload is Node)
    }

    @Test
    fun `no entries renders as HTML`() {
        val response = entries(listOf())
        val rendered = renderer.render(request("/"),response)
        assertEquals(ContentType.HTML,rendered.contentType)
        val page = rendered.page
        assertTrue(page.startsWith("<HTML>"))
        assertTrue(page.endsWith("</HTML>"))
    }

    @Test
    fun `one log entry renders as HTML table`() {
        val number = 42
        val time = Instant.now()
        val logger = Log::class
        val record = "stuff we want to record"
        val at = Throwable()
        val response = entries(listOf(Entry(number,time,logger,record, at)))
        val rendered = renderer.render(request("/"),response)
        assertEquals(ContentType.HTML,rendered.contentType)
        val page = rendered.page
        assertTrue(page.startsWith("<HTML>"))
        assertTrue(page.endsWith("</HTML>"))
        assertTrue(page.contains("<table"))
        assertTrue(page.contains("</table>"))
        assertTrue(page.contains(time.toString()))
        assertTrue(page.contains(logger.toString()))
        assertTrue(page.contains(record))
        assertTrue(page.contains(at.toString()))
    }

    @Test
    fun `entry uses empty string when no throwable message`() {
        val logger = Request::class
        val problem = Throwable()

        ExchangeTracker.nextInfo()
        Log.log(logger,problem)

        val entry = Log.entries.find { e -> e.stack == problem }

        assertNotNull(entry)
        assertEquals(problem,entry.stack)
        assertEquals(logger,entry.logger)
        assertEquals("",entry.record)
    }

    @Test
    fun `entry uses throwable message when it exists`() {
        val logger = Request::class
        val message = "In a bottle"
        val problem = Throwable(message)

        ExchangeTracker.nextInfo()
        Log.log(logger,problem)

        val entry = Log.entries.find { e -> e.stack == problem }

        assertNotNull(entry)
        assertEquals(problem,entry.stack)
        assertEquals(logger,entry.logger)
        assertEquals(message,entry.record)
    }

}