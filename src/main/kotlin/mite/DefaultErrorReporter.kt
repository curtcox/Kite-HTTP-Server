package mite

import mite.html.*
import mite.http.HTTP.*
import mite.ihttp.InternalHttp.*

object DefaultErrorReporter {

    val renderer = DefaultObjectRenderer

    fun render(request: InternalRequest, response: InternalResponse?, t:Throwable) =
        Response.Body(Page(title="$t", bodyText = bodyText(request,response,t)),StatusCode.NOT_IMPLEMENTED)

    fun bodyText(request: InternalRequest,response: InternalResponse?, t:Throwable) : HTML =
        HTML.Tags.combine(renderer.render(request), renderer.render(response), renderer.render(t))

}