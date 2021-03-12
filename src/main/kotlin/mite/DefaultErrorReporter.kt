package mite

import mite.html.*
import mite.http.HTTP.*
import mite.ihttp.InternalHttp.*

object DefaultErrorReporter {

    val renderer = DefaultObjectRenderer

    fun render(request: InternalRequest, response: InternalResponse?, t:Throwable) =
        Response.Body(Page(
            "$t", renderer.render(request), renderer.render(response), renderer.render(t)
        ),StatusCode.NOT_IMPLEMENTED)

}