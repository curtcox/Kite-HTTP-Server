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

    override fun handles(request: InternalRequest, response: InternalResponse) = response.payload is Source

    override fun render(request: InternalRequest, response: InternalResponse) =
        render(response.payload as Source,response.status)

    private fun render(source: Source,status:HTTP.StatusCode) =
        Body(Page(title="${source.file.absolutePath}",bodyText = html(source.lines)),status)

    private fun html(lines: List<String>) =
        Tags.string(Tags.pre(Tags.code(source(lines).joinToString(separator = System.lineSeparator()))))

    private fun source(lines: List<String>) = lines.mapIndexed { index, s -> line(index + 1,s) }

    private fun line(index: Int, line: String) = "<a>$index</a> $line"

}