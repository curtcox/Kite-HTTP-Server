package mite.html

interface HTML {

    interface Renderer {
        fun renders(a:Any?) : Boolean
        fun render(a:Any?) : HTML
    }

    fun toHtml() : String

    object Tags {
        fun    pre(text:String) = tag(text,"<pre>","</pre>")
        fun   code(text:String) = tag(text,"<code>","</code>")
        fun script(text:String) = tag(text,"<script>","</script>")
        fun combine(vararg html:HTML) = object : HTML {
            override fun toHtml() = html.map{ x -> x.toHtml() }  .joinToString(" ")
        }
        fun tag(text:String,open:String,close:String) =
"""
$open
$text
$close
""".trim()

        fun string(a:Any) = object : HTML {
            override fun toHtml() = "$a"
        }
    }

}