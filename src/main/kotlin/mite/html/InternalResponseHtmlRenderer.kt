package mite.html

import mite.ihttp.InternalHttp.*

object InternalResponseHtmlRenderer : HTML.Renderer {

    override fun renders(a: Any?) = a is InternalResponse

    override fun render(a: Any?): HTML = table(a as InternalResponse)

    private fun table(t:InternalResponse) = Table(head(),body(t),"Response")

    private fun head() = Table.Row(listOf("field", "value"), "TH")

    private fun body(request: InternalResponse) = Table.Body(
        listOf(
            Table.Row(listOf("payload", request.payload.toString()),"TD"),
            Table.Row(listOf("status",  request.status.toString()),"TD"),
            Table.Row(listOf("renderer",request.renderer.toString()),"TD"),
        )
    )

}