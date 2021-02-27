package mite.handlers

import mite.http.HTTP.*
import java.io.File
import mite.ihttp.InternalHttp.*

object ResourceHandler : BodyHandler {

    override fun handle(request: Request) = InternalResponse.OK(File(path(request)).readBytes(), mimeType(request)!!)

    override fun handles(request: Request) = mimeType(request) != null

    private fun path(request: Request) = "resources" + request.filename

    private fun mimeType(request: Request) = when {
        request.filename.endsWith(".ico") -> ContentType.ICON
        request.filename.endsWith(".js")  -> ContentType.JAVASCRIPT
        request.filename.endsWith(".css") -> ContentType.CSS
        else -> null
    }
}