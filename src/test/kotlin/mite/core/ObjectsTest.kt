package mite.core

import mite.*
import mite.ast.*
import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*
import mite.ihttp.InternalHttp.*

class ObjectsTest {

    private val renderer = Objects.renderer

    fun request(filename:String) = TestObjects.internalRequestForFilename(filename)

    fun objects(entries:List<Objects.SingleObject>) = InternalResponse.node(SimpleNode.list(Log::class, entries))

    @Test
    fun `handles expected requests`() {
        assertTrue(Objects.handles(request("/object")))
    }

    @Test
    fun `ignores other requests`() {
        assertFalse(Objects.handles(request("/schmog")))
    }

    @Test
    fun `response is HTML`() {
        val response = Objects.handle(request("/object"))
        assertEquals(StatusCode.OK,response.status)
        assertTrue(response.payload is Node)
    }

    @Test
    fun `no objects renders as HTML`() {
        val response = objects(listOf())

        ExchangeTracker.nextInfo()
        val rendered = renderer.render(request("/object"),response)

        assertEquals(ContentType.HTML,rendered.contentType)
        val page = PageAsserts(rendered.page)
        page.startsWith("<HTML>")
        page.endsWith("</HTML>")
    }

    @Test
    fun `one object entry renders as HTML table`() {
        val record = "stuff we want to record"
        val response = objects(listOf(Objects.SingleObject(record)))

        ExchangeTracker.nextInfo()
        val rendered = renderer.render(request("/object/${record.hashCode()}"),response)

        assertEquals(ContentType.HTML,rendered.contentType)
        val page = PageAsserts(rendered.page)
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
        page.contains("""<table id="List_table" class="display responsive wrap" style="width:100%">""")
        page.contains("""<TR><TH>#</TH><TH>o</TH></TR>""")
        page.contains("""<TR><TD><a href="/object/-571983892@0">0</a></TD><TD>stuff we want to record</TD></TR>""")
    }

    @Test
    fun `linkTo object can be used to get object`() {
        val record = "link to stuff we want to record"

        ExchangeTracker.nextInfo()
        val link = Objects.linkTo(record)
        val request = request(link)
        val response = Objects.handle(request)
        val rendered = renderer.render(request,response)

        assertEquals(ContentType.HTML,rendered.contentType)
        val page = PageAsserts(rendered.page)
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
        page.contains("""<table id="Map_table" class="display responsive wrap" style="width:100%">""")
        page.contains("""<TR><TH>Key</TH><TH>Value</TH></TR>""")
        page.contains("""<TR><TD><a href="/object@0@o">o</a></TD><TD>link to stuff we want to record</TD></TR>""")
    }

    @Test
    fun `linkTo object returns response with single object as payload`() {
        val record = "link to stuff we want to record"

        ExchangeTracker.nextInfo()
        val request = request(Objects.linkTo(record))
        val response = Objects.handle(request)

        val payload = response.payload
        assertTrue(payload is ReflectiveNode)
        val node = payload
        assertTrue(node is ReflectiveNode)
        val value = node.nodeValue
        assertTrue(value is Objects.SingleObject)
        val single = value
        assertEquals(record,single.o)
    }

    @Test
    fun `linkTo object returns response with default renderer`() {
        ExchangeTracker.nextInfo()
        val link = Objects.linkTo("???")

        val response = Objects.handle(request(link))

        assertEquals(Objects.renderer,response.renderer)
    }

    @Test
    fun `linkTo object returns success`() {
        ExchangeTracker.nextInfo()
        val link = Objects.linkTo("???")

        val response = Objects.handle(request(link))

        assertEquals(StatusCode.OK,response.status)
    }

}