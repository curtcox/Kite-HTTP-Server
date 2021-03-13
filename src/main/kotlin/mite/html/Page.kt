package mite.html

import mite.html.HTML.Tags.tag

data class Page(val css:String="",val script:String="",val title:String,val bodyText:HTML) : HTML {

    override fun toHtml() = html(head(title(title)) + body(combinedBody()))

    private fun combinedBody() =
        """
            $css
            $script
            ${h1(title)}
            ${bodyText.toHtml()}
        """.trimIndent()


    private fun html(text:String) = tag(text,"<HTML>","</HTML>")
    private fun body(text:String) = tag(text,"<BODY>","</BODY>")
    private fun head(text:String) = tag(text,"<head>","</head>")
    private fun title(text:String) = tag(text,"<title>","</title>")
    private fun h1(text:String) = tag(text,"<h1>","</h1>")

}