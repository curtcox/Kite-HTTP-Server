package mite.core

import mite.ast.Node
import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*

class ObjectsTest {

    val renderer = Objects.renderer

    fun request(filename:String) =
        Request(arrayOf(""),Request.Method.UNKNOWN,"",filename,ContentType.FORM_URLENCODED,Version.Unknown)

    fun entries(entries:List<Objects.SingleObject>) = InternalResponse.node(Node.list(Log::class, entries))

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
        val response = entries(listOf())
        val rendered = renderer.render(request("/"),response)
        assertEquals(ContentType.HTML,rendered.contentType)
        val page = rendered.page
        assertTrue(page.startsWith("<HTML>"))
        assertTrue(page.endsWith("</HTML>"))
    }

    @Test
    fun `one object entry renders as HTML table`() {
        val record = "stuff we want to record"
        val response = entries(listOf(Objects.SingleObject(record)))
        val rendered = renderer.render(request("/"),response)
        assertEquals(ContentType.HTML,rendered.contentType)
        val page = rendered.page
        assertTrue(page.startsWith("<HTML>"),page)
        assertTrue(page.endsWith("</HTML>"),page)
        assertTrue(page.contains("<TABLE>"),page)
        assertTrue(page.contains("</TABLE>"),page)
        assertTrue(page.contains(record),page)
    }

}