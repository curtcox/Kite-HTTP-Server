package mite

import mite.html.*
import mite.html.Table.*
import mite.http.HTTP.*
import mite.ihttp.InternalHttp.*

object DefaultErrorReporter {

    fun render(request: InternalRequest, response: InternalResponse?, t:Throwable) =
        Response.Body(Page(
            string(request), string(response),table(t)
        ),StatusCode.NOT_IMPLEMENTED)

    private fun table(t:Throwable) = Table(head(),body(t))

    private fun head() = Row(listOf("Class Name","File Name","line number"),"TH")

    private fun body(t:Throwable) = Body(t.stackTrace.map { e -> row(e) })

    private fun row(element: StackTraceElement) = Row(listOf(
        "${element.className}",
        "${element.fileName}",
        "${element.lineNumber}"
    ),"TD")

    private fun string(a:Any?) = object : HTML {
        override fun toHtml() = "$a"
    }

}