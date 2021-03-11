package mite.renderers

import mite.PageAsserts
import mite.http.HTTP.*
import org.junit.Test
import mite.ihttp.InternalHttp.*
import kotlin.test.assertEquals

class SourceRendererTest {

    val         raw = Request.Raw(arrayOf())
    val      method = Request.Method.GET
    val        host = ""
    val    filename = ""
    val contentType = ContentType.FORM_URLENCODED
    val httpVersion = Version._1_1
    val      status = StatusCode.OK

    val renderer = SourceRenderer

    @Test
    fun `empty payload produces page with empty body`() {
        val  request = InternalRequest(Request(raw,method,host,filename,contentType,httpVersion))
        val  payload = listOf<String>()
        val response = InternalResponse(payload,status)

        val body = renderer.render(request,response)

        assertEquals(ContentType.HTML,body.contentType)

        val page = PageAsserts(body.page)

        page.startsWith("<HTML>")
        page.endsWith("</HTML>")
    }

    @Test
    fun `payload with one line produces page with one line`() {
        val  request = InternalRequest(Request(raw,method,host,filename,contentType,httpVersion))
        val  payload = listOf("what the line says")
        val response = InternalResponse(payload,status)

        val body = renderer.render(request,response)

        assertEquals(ContentType.HTML,body.contentType)

        val page = PageAsserts(body.page)

        page.startsWith("<HTML>")
        page.contains("<a>1</a>what the line says")
        page.endsWith("</HTML>")
    }

}