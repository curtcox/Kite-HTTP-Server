package mite.html

/**
 * Renderer for many single specific objects.
 */
object SpecificObjectRenderer : HTML.Renderer {

    val inner = CompositeHtmlRenderer(
        InternalRequestHtmlRenderer,
        InternalResponseHtmlRenderer,
        ThrowableRenderer
    )

    override fun renders(a: Any?) = inner.renders(a)
    override fun render(a: Any?)  = inner.render(a)
}