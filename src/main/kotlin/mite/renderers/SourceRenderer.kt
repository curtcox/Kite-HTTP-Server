package mite.renderers

import mite.handlers.SourceHandler
import mite.html.Page
import mite.http.HTTP.*
import mite.http.HTTP.Response.*
import mite.ihttp.InternalHttp.*
import mite.ihttp.InternalHttp.InternalResponse.*
import java.io.File

object SourceRenderer : Renderer {

    override fun handles(request: InternalRequest, response: InternalResponse) = response.payload is List<*>

    override fun render(request: InternalRequest, response: InternalResponse) =
        Body(Page(),response.status)

//    private fun source(request: InternalRequest) = file(request).readLines().mapIndexed { index, s -> line(index,s) }
//
//    private fun line(index: Int, line: String) = "<a>$index</a>$line"
//
//    private fun file(request: InternalRequest) = File(SourceHandler.path(request))

}