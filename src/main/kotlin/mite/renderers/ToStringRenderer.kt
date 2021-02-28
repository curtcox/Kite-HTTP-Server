package mite.renderers

import mite.http.HTTP.*
import mite.http.HTTP.Response.*
import mite.ihttp.InternalHttp.*
import mite.ihttp.InternalHttp.InternalResponse.*

object ToStringRenderer : UnconditionalRenderer() {
    override fun render(request: InternalRequest, inner: InternalResponse) =
        Body(inner.toString().toByteArray(), ContentType.TEXT, inner.status)
}