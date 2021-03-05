package mite.renderers

import mite.html.HTML.Tags
import mite.html.Page
import mite.http.HTTP.Response.*
import mite.ihttp.InternalHttp.*
import mite.ihttp.InternalHttp.InternalResponse.*

object SourceRenderer : Renderer {

    override fun handles(request: InternalRequest, response: InternalResponse) = response.payload is List<*>

    override fun render(request: InternalRequest, response: InternalResponse) =
        Body(Page(html(response.payload as List<String>)),response.status)

    private fun html(lines: List<String>) =
        Tags.string(Tags.pre(Tags.code(source(lines).joinToString(separator = System.lineSeparator()))))

    private fun source(lines: List<String>) = lines.mapIndexed { index, s -> line(index + 1,s) }

    private fun line(index: Int, line: String) = "<a>$index</a>$line"

}