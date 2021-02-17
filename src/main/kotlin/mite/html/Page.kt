package mite.html

data class Page(val bodyText:HTML) : HTML {

    override fun toHtml() = html(body(bodyText.toHtml()))

    fun html(text:String) = tag(text,"<HTML>","</HTML>")
    fun body(text:String) = tag(text,"<BODY>","</BODY>")
    fun head(text:String) = tag(text,"<HEAD>","</HEAD>")
    fun title(text:String) = tag(text,"<TITLE>","</TITLE>")

}