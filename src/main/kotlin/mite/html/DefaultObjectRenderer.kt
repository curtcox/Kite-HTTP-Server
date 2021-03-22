package mite.html

/**
 * Renderer for any object.
 */
object DefaultObjectRenderer : HTML.Renderer {

    val inner = CompositeHtmlRenderer(
        SpecificObjectRenderer,
        ArbitraryObjectHtmlRenderer
    )

    override fun renders(a: Any?) = inner.renders(a)
    override fun render(a: Any?)  = inner.render(a)
}