package mite.handlers

import mite.http.HTTP.*
import java.io.File
import mite.ihttp.InternalHttp.*
import mite.payloads.Source
import mite.renderers.SourceRenderer

object SourceHandler : BodyHandler {

    override fun handle(request: InternalRequest) = InternalResponse(source(request),StatusCode.OK, SourceRenderer)

    override fun handles(request: InternalRequest) = request.filename.startsWith("/source/")

    private fun source(request: InternalRequest) : Source {
        val file = file(request)
        return Source(file,file.readLines())
    }

    private fun file(request: InternalRequest) = File(path(request))

    private fun path(request: InternalRequest) = "src/main/kotlin/" + request.withoutPrefix("/source").filename

}