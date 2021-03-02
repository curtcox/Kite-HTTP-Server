package mite

import mite.html.*
import mite.http.HTTP.*
import mite.ihttp.InternalHttp.*

object DefaultErrorReporter {

    val renderer = DefaultObjectRenderer

    fun render(request: InternalRequest, response: InternalResponse?, t:Throwable) =
        Response.Body(Page(
            renderer.render(request), string(response), renderer.render(t)
        ),StatusCode.NOT_IMPLEMENTED)


    private fun string(a:Any?) = object : HTML {
        override fun toHtml() = "$a"
    }

}