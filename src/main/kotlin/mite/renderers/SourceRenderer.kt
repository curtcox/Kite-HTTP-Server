package mite.renderers

import mite.html.HTML.Tags
import mite.html.Page
import mite.http.HTTP
import mite.http.HTTP.Response.*
import mite.ihttp.InternalHttp.*
import mite.ihttp.InternalHttp.InternalResponse.*
import mite.payloads.Source

//??? https://highlightjs.org/usage/
object SourceRenderer : Renderer {

    private val css = """<link rel="stylesheet" href="/github.css">"""
    private val script = """
        <script src="/highlight.pack.js"></script>
        <script>hljs.highlightAll();</script>
    """.trimIndent()

    override fun handles(request: InternalRequest, response: InternalResponse) = response.payload is Source

    override fun render(request: InternalRequest, response: InternalResponse) =
        render(response.payload as Source,response.status)

    private fun render(source: Source,status:HTTP.StatusCode) =
        Body(Page(css=css,script = script,title="${source.file.absolutePath}",bodyText = html(source.lines)),status)

    private fun html(lines: List<String>) =
        Tags.string(Tags.pre(Tags.code(source(lines).joinToString(separator = System.lineSeparator()))))

    private fun source(lines: List<String>) = lines.mapIndexed { index, s -> line(index,s) }

    private fun line(index: Int, line: String) = "<a>${number(index + 1)}</a> $line"

    private fun number(index: Int) = if (index<10) "0$index" else "$index"
}