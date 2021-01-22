package mite.renderers

import mite.ast.Node
import mite.core.Log
import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*

class HtmlRendererTest {

    val renderer = HtmlRenderer()
    val request = Request(
        arrayOf(), Request.Method.UNKNOWN, "","",
        ContentType.FORM_URLENCODED, Version.Unknown
    )


    fun entries(entries:List<Node>) = InternalResponse.node(Node.list(Log::class, entries),HtmlRenderer())

    @Test
    fun `no entries renders as HTML`() {
        val response = entries(listOf())
        val rendered = renderer.render(request,response)
        assertEquals(ContentType.HTML,rendered.contentType)
        val page = rendered.page
        assertTrue(page.startsWith("<HTML>"))
        assertTrue(page.endsWith("</HTML>"))
    }
}