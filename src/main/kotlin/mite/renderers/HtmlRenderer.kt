package mite.renderers

import mite.ast.Node
import mite.ast.Node.*
import mite.html.*
import mite.http.HTTP.Response.*
import mite.ihttp.InternalHttp.*

/**
 * For rendering internal responses as HTML.
 */
data class HtmlRenderer(val nodeRenderer:Renderer) : InternalResponse.UnconditionalRenderer() {

    private val escaped = object : Renderer {
        override fun header(nodes: List<*>): List<String> = nodeRenderer.header(nodes).map { s -> escape(s) }
        override fun render(node: Node):     List<String> = nodeRenderer.render(node).map  { s -> escape(s) }
    }

    private fun escape(html:String,max:Int=500) = Escaper.escape(html,max)

    override fun render(request: InternalRequest, response: InternalResponse): Body {
        if (response.payload is Node) {
            return Body(page(request,response.payload),response.status)
        }
        TODO("Not yet implemented")
    }

    private fun page(request: InternalRequest, payload: Node) =
        Page.of(title = title(payload),bodyText = bodyText(request,payload))

    private fun bodyText(request: InternalRequest, node: Node): HTML {
        val specific = RequestSpecificHtmlRenderer(request,escaped).node(node)
        val renderer = SpecificObjectRenderer
        val value = node.value
        if (renderer.renders(value)) {
            println("value = ${value::class}")
        }
        return if (renderer.renders(value)) HTML.Tags.combine(renderer.render(value),specific) else specific
    }

    private fun title(payload:Any) = escape("$payload",70)
}