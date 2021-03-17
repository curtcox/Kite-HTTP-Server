package mite.renderers

import mite.ast.Node
import mite.ast.Node.*
import mite.html.Escaper
import mite.html.Page
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
            val inner = RequestSpecificHtmlRenderer(request,escaped)
            val payload = response.payload
            val page = Page.of(title = title(payload),bodyText = inner.node(payload))
            return Body(page,response.status)
        }
        TODO("Not yet implemented")
    }

    private fun title(payload:Any) = escape("$payload",50)
}