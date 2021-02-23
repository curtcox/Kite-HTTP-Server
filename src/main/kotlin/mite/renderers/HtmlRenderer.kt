package mite.renderers

import mite.ast.Node
import mite.ast.Node.*
import mite.html.Page
import mite.http.HTTP.*
import mite.http.HTTP.Response.*

/**
 * For rendering internal responses as HTML.
 */
class HtmlRenderer(val nodeRenderer:Renderer) : Body.UnconditionalRenderer() {

    override fun render(request: Request, response: InternalResponse): Body {
        if (response.contentType == ContentType.AST) {
            val inner = RequestSpecificHtmlRenderer(request,nodeRenderer)
            val page = Page(inner.node(response.payload as Node))
            return Body(page,response.status)
        }
        TODO("Not yet implemented")
    }

}