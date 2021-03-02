package mite.html

import mite.html.HTML.Tags.tag

class Page(vararg bodyText:HTML) : HTML {

    private val text = bodyText

    override fun toHtml() = html(body(combinedBody()))

    private fun combinedBody() : String {
        val out = StringBuilder()
        for (t in text) {
            out.append(t.toHtml())
        }
        return out.toString()
    }

    fun html(text:String) = tag(text,"<HTML>","</HTML>")
    fun body(text:String) = tag(text,"<BODY>","</BODY>")

}