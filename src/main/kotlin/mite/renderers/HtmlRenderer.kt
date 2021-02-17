package mite.renderers

import mite.ast.Node
import mite.ast.Node.*
import mite.html.Page
import mite.http.HTTP.*

/**
 * For rendering internal responses as HTML.
 */
class HtmlRenderer(val nodeRenderer:Renderer) : Response.UnconditionalRenderer() {

    override fun render(request: Request, response: InternalResponse): Response {
        if (response.contentType == ContentType.AST) {
            val inner = RequestSpecificHtmlRenderer(request,nodeRenderer)
            val page = Page(inner.node(response.payload as Node))
            return Response(page,response.status)
        }
        TODO("Not yet implemented")
    }

}