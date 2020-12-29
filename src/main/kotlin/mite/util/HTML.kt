package mite.util

interface HTML {

    fun body(text:String) = tag(text,"<BODY>","</BODY>")
    fun head(text:String) = tag(text,"<HEAD>","</HEAD>")
    fun title(text:String) = tag(text,"<TITLE>","</TITLE>")
    fun h1(text:String) = tag(text,"<H1>","</H1>")
    fun pre(text:String) = tag(text,"<PRE>","</PRE>")
    fun html(text:String) = tag(text,"<HTML>","</HTML>")

    fun tag(text:String,open:String,close:String) =
        """
$open
$text
$close
"""

}