package mite.html

import mite.html.HTML.Tags.tag

class Page(val title:String,vararg bodyText:HTML) : HTML {

    private val text = bodyText

    override fun toHtml() = html(head(title(title)) + body(combinedBody()))

    private fun combinedBody() : String {
        val out = StringBuilder(h1(title))
        for (t in text) {
            out.append(t.toHtml())
        }
        return out.toString()
    }

    private fun html(text:String) = tag(text,"<HTML>","</HTML>")
    private fun body(text:String) = tag(text,"<BODY>","</BODY>")
    private fun head(text:String) = tag(text,"<head>","</head>")
    private fun title(text:String) = tag(text,"<title>","</title>")
    private fun h1(text:String) = tag(text,"<h1>","</h1>")

}