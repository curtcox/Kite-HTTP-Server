package mite.core

import mite.ast.Node
import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*

class ObjectsTest {

    val renderer = Objects.renderer

    fun request(filename:String) =
        Request(arrayOf(""),Request.Method.UNKNOWN,"",filename,ContentType.FORM_URLENCODED,Version.Unknown)

    fun objects(entries:List<Objects.SingleObject>) = InternalResponse.node(Node.list(Log::class, entries))

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
        assertEquals(ContentType.AST,response.contentType)
        assertEquals(StatusCode.OK,response.status)
        assertTrue(response.payload is Node)
    }

    @Test
    fun `no objects renders as HTML`() {
        val response = objects(listOf())
        val rendered = renderer.render(request("/object"),response)
        assertEquals(ContentType.HTML,rendered.contentType)
        val page = rendered.page
        assertTrue(page.startsWith("<HTML>"))
        assertTrue(page.endsWith("</HTML>"))
    }

    @Test
    fun `one object entry renders as HTML table`() {
        val record = "stuff we want to record"
        val response = objects(listOf(Objects.SingleObject(record)))
        val rendered = renderer.render(request("/object/${record.hashCode()}"),response)
        assertEquals(ContentType.HTML,rendered.contentType)
        val page = rendered.page
        assertEquals("""
            <HTML>
            <BODY>
            <TABLE>
            <TR><TH>Class</TH><TH>Id</TH><TH>String</TH></TR>
            <TR><TD>String</TD><TD>${record.hashCode()}</TD><TD>stuff we want to record</TD></TR>
            </TABLE>
            </BODY>
            </HTML>
        """.trimIndent(),page)
    }

}