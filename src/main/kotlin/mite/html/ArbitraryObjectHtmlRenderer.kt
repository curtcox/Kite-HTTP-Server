package mite.html

import mite.html.HTML.Renderer

object ArbitraryObjectHtmlRenderer : Renderer {

    override fun renders(a: Any?) = true

    override fun render(a:Any?) = object : HTML {
        override fun toHtml() = "$a"
    }

}