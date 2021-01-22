package mite.renderers

import mite.ast.Node
import mite.core.Log
import mite.core.Log.Entry
import mite.http.HTTP.*
import org.junit.Test
import java.time.Instant
import kotlin.test.*

class HtmlRendererTest {

    val renderer = HtmlRenderer()
    val request = Request(
        arrayOf(), Request.Method.UNKNOWN, "","",
        ContentType.FORM_URLENCODED, Version.Unknown
    )


    fun entries(entries:List<Entry>) = InternalResponse.node(Node.list(Log::class, entries),HtmlRenderer())

    @Test
    fun `no entries renders as HTML`() {
        val response = entries(listOf())
        val rendered = renderer.render(request,response)
        assertEquals(ContentType.HTML,rendered.contentType)
        val page = rendered.page
        assertTrue(page.startsWith("<HTML>"))
        assertTrue(page.endsWith("</HTML>"))
    }

    @Test
    fun `one entry renders as HTML table`() {
        val time = Instant.now()
        val logger = this
        val record = "stuff we want to record"
        val at = Throwable()
        val response = entries(listOf(Entry(time,logger,record, at)))
        val rendered = renderer.render(request,response)
        assertEquals(ContentType.HTML,rendered.contentType)
        val page = rendered.page
        assertTrue(page.startsWith("<HTML>"))
        assertTrue(page.endsWith("</HTML>"))
        assertTrue(page.contains("<TABLE>"))
        assertTrue(page.contains("</TABLE>"))
        assertTrue(page.contains(time.toString()))
        assertTrue(page.contains(logger.toString()))
        assertTrue(page.contains(record))
        assertTrue(page.contains(at.toString()))
    }

}