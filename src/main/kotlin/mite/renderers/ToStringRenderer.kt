package mite.renderers

import mite.http.HTTP

object ToStringRenderer : HTTP.Response.UnconditionalRenderer() {
    override fun render(request: HTTP.Request, inner: HTTP.InternalResponse) =
        HTTP.Response(inner.toString().toByteArray(), HTTP.ContentType.TEXT, inner.status)
}