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
        val rendered = renderer.render(request("/object/${record.hashCode()}"),response)
        assertEquals(ContentType.HTML,rendered.contentType)
        val page = PageAsserts(rendered.page)
        page.startsWith("""
            <HTML>
            <BODY>
        """.trimIndent())
        page.endsWith("""
            </table>
            </BODY>
            </HTML>
        """.trimIndent())
        page.contains("""<table id="table_id" class="display responsive wrap" style="width:100%">""")
        page.contains("""<TR><TH>#</TH><TH>Class</TH><TH>Id</TH><TH>String</TH></TR>""")
        page.contains("""<TR><TD><a href="/object/-571983892@0">0</a></TD><TD>String</TD><TD>-571983892</TD><TD>stuff we want to record</TD></TR>""")
    }

}