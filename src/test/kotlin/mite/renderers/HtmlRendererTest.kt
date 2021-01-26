package mite.renderers

import mite.ast.Node
import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*

class HtmlRendererTest {

    val request = Request(
        arrayOf(), Request.Method.UNKNOWN, "","",
        ContentType.FORM_URLENCODED, Version.Unknown
    )

    private fun singletons(list:List<Singleton>) = InternalResponse.node(Node.list(Singleton::class, list))
    private fun leaf(one:Singleton) = InternalResponse.node(Node.leaf(Singleton::class, one))

    private data class Singleton(val value:String)
    private val singletonRenderer = object : Node.Renderer  {
        override fun header() = "<TR><TH>Value</TH></TR>"
        override fun render(node: Node): String {
            val entry = node.leaf as Singleton
            return "<TR><TD>${entry.value}</TD></TR>"
        }
    }

    @Test
    fun `a list of one singleton renders as HTML table`() {
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

    @Test
    fun `a node of one singleton renders as HTML table`() {
        val renderer = HtmlRenderer(singletonRenderer)
        val value = "stuff"
        val one = Singleton(value)
        val response = leaf(one)
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