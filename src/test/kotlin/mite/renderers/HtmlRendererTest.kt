package mite.renderers

import mite.ast.Node
import mite.core.Log
import mite.core.Log.Entry
import mite.http.HTTP.*
import org.junit.Test
import java.time.Instant
import kotlin.test.*

class HtmlRendererTest {

    val request = Request(
        arrayOf(), Request.Method.UNKNOWN, "","",
        ContentType.FORM_URLENCODED, Version.Unknown
    )

    fun entries(entries:List<Entry>) = InternalResponse.node(Node.list(Log::class, entries))
    private fun singletons(entries:List<Singleton>) = InternalResponse.node(Node.list(Log::class, entries))

    @Test
    fun `no entries renders as HTML`() {
        val renderer = Log.renderer
        val response = entries(listOf())
        val rendered = renderer.render(request,response)
        assertEquals(ContentType.HTML,rendered.contentType)
        val page = rendered.page
        assertTrue(page.startsWith("<HTML>"))
        assertTrue(page.endsWith("</HTML>"))
    }

    @Test
    fun `one log entry renders as HTML table`() {
        val renderer = Log.renderer
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

    private data class Singleton(val value:String)
    private val singletonRenderer = object : Node.Renderer  {
        override fun header() = "<TR><TH>Value</TH></TR>"
        override fun render(node: Node): String {
            val entry = node.leaf as Singleton
            return "<TR><TD>${entry.value}</TD></TR>"
        }
    }

    @Test
    fun `one singleton renders as HTML table`() {
        val renderer = HtmlRenderer(singletonRenderer)
        val value = "stuff"
        val one = Singleton(value)
        val response = singletons(listOf(one))
        val rendered = renderer.render(request,response)
        assertEquals(ContentType.HTML,rendered.contentType)
        val page = rendered.page
        assertEquals("""
            <HTML>
            <BODY>
            <TABLE>
            <TR><TH>Value</TH></TR>
            <TR><TD>stuff</TD></TR>
            </TABLE>
            </BODY>
            </HTML>
        """.trimIndent(), page)
    }

}