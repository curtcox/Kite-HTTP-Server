package mite.html

import mite.ihttp.InternalHttp

import mite.ihttp.InternalHttp.*

object InternalRequestHtmlRenderer : HTML.Renderer {

    override fun renders(a: Any?) = a is InternalRequest

    override fun render(a: Any?): HTML = table(a as InternalRequest)

    private fun table(t:InternalRequest) = Table(head(),body(t))

    private fun head() = Table.Row(listOf("field", "value"), "TH")

    private fun body(request: InternalRequest) = Table.Body(
        listOf(
            Table.Row(listOf("method",request.method.toString()),"TD"),
            Table.Row(listOf("filename",request.filename),"TD"),
            Table.Row(listOf("HTTP version",request.httpVersion.toString()),"TD"),
            Table.Row(listOf("host",request.host),"TD"),
            Table.Row(listOf("request",request.request.toString()),"TD"),
            Table.Row(listOf(),"TD")
        )
    )

}