package mite.renderers

import mite.*
import mite.ast.*
import mite.core.ExchangeTracker
import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*
import mite.ihttp.InternalHttp.*

class HtmlRendererTest {

    val request = TestObjects.request

    private fun pairs(map:Map<String,Singleton>) = InternalResponse.node(SimpleNode.map(Singleton::class, map))
    private fun singletons(list:List<Singleton>) = InternalResponse.node(SimpleNode.list(Singleton::class, list))
    private fun leaf(one:Singleton) = InternalResponse.node(SimpleNode.leaf(Singleton::class, one))

    private data class Singleton(val value:String)
    private val singletonRenderer = object : Node.Renderer  {
        override fun header(list:List<*>) : List<String> = listOf("Value")
        override fun render(node: Node) : List<String> {
            val entry = node.leaf as Singleton
            return listOf(entry.value)
        }
    }

    private fun assertPageWithHtmTable(page: PageAsserts) {
        page.startsWith("""
            <HTML>
            <head>
            <title>
        """.trimIndent())
        page.endsWith("""
            </table>
            </BODY>
            </HTML>
        """.trimIndent())
        page.contains("<table")
    }

    private fun assertTableHeadAndBodyExist(page: PageAsserts) {
        page.contains("""<thead>""")
        page.contains("""</thead>""")
        page.contains("""<tbody>""")
        page.contains("""</tbody>""")
    }

    @Test
    fun `a list of one singleton renders as HTML table`() {
        val renderer = HtmlRenderer(singletonRenderer)
        val value = "stuff"
        val one = Singleton(value)
        val response = singletons(listOf(one))

        ExchangeTracker.nextInfo()
        val rendered = renderer.render(request,response)

        assertEquals(ContentType.HTML,rendered.contentType)
        val page = PageAsserts(rendered.page)
        assertPageWithHtmTable(page)
        assertTableHeadAndBodyExist(page)
        page.contains("""<TR><TH>#</TH><TH>Value</TH></TR>""")
        page.contains("""<tbody><TR><TD><a href="@0">0</a></TD><TD>stuff</TD></TR></tbody>""")
    }

    @Test
    fun `a map of one singleton renders as HTML table`() {
        val renderer = HtmlRenderer(singletonRenderer)
        val value = "thing"
        val one = Singleton(value)
        val response = pairs(mapOf("one" to one))

        ExchangeTracker.nextInfo()
        val rendered = renderer.render(request,response)

        assertEquals(ContentType.HTML,rendered.contentType)
        val page = PageAsserts(rendered.page)
        assertPageWithHtmTable(page)
        assertTableHeadAndBodyExist(page)
        page.contains("""<TR><TH>Key</TH><TH>Value</TH></TR>""")
        page.contains("""<tbody><TR><TD><a href="@one">one</a></TD><TD>Singleton(value=thing)</TD></TR></tbody>""")
    }

    @Test
    fun `a node of one singleton renders as HTML table`() {
        val renderer = HtmlRenderer(singletonRenderer)
        val value = "stuff"
        val one = Singleton(value)
        val response = leaf(one)

        ExchangeTracker.nextInfo()
        val rendered = renderer.render(request,response)

        assertEquals(ContentType.HTML,rendered.contentType)
        val page = PageAsserts(rendered.page)
        assertPageWithHtmTable(page)
        assertTableHeadAndBodyExist(page)
        page.contains("""<TR><TH>#</TH><TH>Value</TH></TR>""")
        page.contains("""<tbody><TR><TD><a href="@0">0</a></TD><TD>stuff</TD></TR></tbody>""")
    }

}