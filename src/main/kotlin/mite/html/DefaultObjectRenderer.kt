package mite.html

object DefaultObjectRenderer : HTML.Renderer {

    val inner = CompositeHtmlRenderer(
        InternalRequestHtmlRenderer,
        InternalResponseHtmlRenderer,
        ThrowableRenderer,
        ArbitraryObjectHtmlRenderer
    )

    override fun renders(a: Any?) = inner.renders(a)
    override fun render(a: Any?)  = inner.render(a)
}