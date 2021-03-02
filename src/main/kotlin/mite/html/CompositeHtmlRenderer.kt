package mite.html

import mite.html.HTML.Renderer
import java.lang.IllegalArgumentException

class CompositeHtmlRenderer(vararg renderers:Renderer) : Renderer {

    private val renderers = renderers

    override fun renders(a: Any?) = renderers.fold(false) { found, r -> found || r.renders(a) }

    override fun render(a: Any?): HTML {
        for (renderer in renderers) {
            if (renderer.renders(a)) {
                return renderer.render(a)
            }
        }
        throw IllegalArgumentException()
    }
}