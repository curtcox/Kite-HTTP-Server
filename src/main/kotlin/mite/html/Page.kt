package mite.html

import mite.core.Log
import mite.core.Objects
import mite.html.HTML.Tags.tag

/**
 * An HTML page. Create with of.
 */
data class Page(
    val css:String,
    val script:String,
    val title:String,
    val bodyText:HTML,
    val created : Throwable) : HTML
{

    override fun toHtml() = html(head(title(title)) + body(combinedBody()))

    private fun combinedBody() =
        """
            $css
            $script
            ${h1(title)}
            ${createdLink()}
            ${bodyText.toHtml()}
        """.trimIndent()


    private fun html(text:String) = tag(text,"<HTML>","</HTML>")
    private fun body(text:String) = tag(text,"<BODY>","</BODY>")
    private fun head(text:String) = tag(text,"<head>","</head>")
    private fun title(text:String) = tag(text,"<title>","</title>")
    private fun h1(text:String) = tag(text,"<h1>","</h1>")
    private fun createdLink() = """<a href="${Objects.linkTo(created)}">@</a>"""

    companion object {
        fun of(css:String="",script:String="",title:String,bodyText:HTML) : Page {
            val t = Throwable()
            val page = Page(css,script,title,bodyText,t)
            Log.log(Page::class,page,t)
            return page
        }
    }
}