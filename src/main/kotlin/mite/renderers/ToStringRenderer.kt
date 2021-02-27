package mite.renderers

import mite.http.HTTP.*
import mite.http.HTTP.Response.*
import mite.ihttp.InternalHttp.*

object ToStringRenderer : Body.UnconditionalRenderer() {
    override fun render(request: Request, inner: InternalResponse) =
        Body(inner.toString().toByteArray(), ContentType.TEXT, inner.status)
}