package mite.html

interface HTML {

    fun toHtml() : String

    fun h1(text:String) = tag(text,"<H1>","</H1>")
    fun pre(text:String) = tag(text,"<PRE>","</PRE>")
    fun script(text:String) = tag(text,"<script>","</script>")

    fun tag(text:String,open:String,close:String) =
"""
$open
$text
$close
""".trim()

}