package mite

import mite.ast.Node
import mite.http.HTTP.*
import org.junit.Test
import kotlin.test.*

class DefaultInternalHandlerTest {

    val handler = DefaultInternalHandler

    fun forFilename(filename:String) =
        Request(Request.Raw(arrayOf()),Request.Method.UNKNOWN,"",filename,ContentType.FORM_URLENCODED,Version.Unknown)

    @Test
    fun `favicon is icon`() {
        val request = forFilename("/favicon.ico")
        val response = handler.handle(request)!!
        assertEquals(ContentType.ICON,response.contentType)
    }

    @Test
    fun `log is AST list`() {
        val request = forFilename("/log")
        val response = handler.handle(request)!!
        assertEquals(ContentType.AST,response.contentType)
        assertEquals(StatusCode.OK,response.status)
        val node = response.payload as Node
        assertEquals(Node.Arity.list,node.arity)
    }

    @Test
    fun `pwd is text`() {
        val request = forFilename("/pwd")
        val response = handler.handle(request)!!
        assertEquals(ContentType.AST,response.contentType)
        assertEquals(StatusCode.OK,response.status)
        val node = response.payload as Node
        assertEquals(Node.Arity.list,node.arity)
        val page = response.payload.toString()
        assertTrue(page.contains("/Users"),page)
        assertTrue(page.contains("/Kite-HTTP-Server"),page)
    }

}