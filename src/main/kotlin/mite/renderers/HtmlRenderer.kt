package mite.renderers

import mite.ast.Node
import mite.http.HTTP.*

class HtmlRenderer(val nodeRenderer:Node.Renderer) : Response.UnconditionalRenderer(), HTML {

    override fun render(request: Request, response: InternalResponse): Response {
        if (response.contentType == ContentType.AST) {
            val s = html(body(node(response.payload as Node)))
            return Response(s.toByteArray(),ContentType.HTML,response.status)
        }
        TODO("Not yet implemented")
    }

    private fun node(node: Node): String {
        if (node.arity == Node.Arity.list) {
            return table(node.list!!)
        }
        TODO("Not yet implemented")
    }

    private fun table(list:List<Node>): String {
        return """
            <TABLE>
            ${nodeRenderer.header()}
            ${rows(list)}
            </TABLE>
        """.trimIndent()
    }

    private fun rows(list:List<Node>): String {
        val rows = StringBuilder()
        for (item in list) {
            rows.append(nodeRenderer.render(item))
        }
        return rows.toString()
    }

}