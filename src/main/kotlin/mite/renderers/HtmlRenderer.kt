package mite.renderers

import mite.ast.Node
import mite.ast.Node.*
import mite.http.HTTP.*

class HtmlRenderer(val nodeRenderer:Renderer) : Response.UnconditionalRenderer(), HTML {

    override fun render(request: Request, response: InternalResponse): Response {
        if (response.contentType == ContentType.AST) {
            val s = html(body(node(response.payload as Node)))
            return Response(s.toByteArray(),ContentType.HTML,response.status)
        }
        TODO("Not yet implemented")
    }

    private fun node(node: Node): String {
        return when (node.arity) {
            (Arity.leaf) -> table(listOf(node))
            (Arity.list) -> table(node.list)
            (Arity.map)  -> table(node.map)
            else -> TODO("Not yet implemented")
        }
    }

    private fun table(list:List<Node>): String {
        return """
            <TABLE>
            ${row(nodeRenderer.header(),"TH")}
            ${rows(list)}
            </TABLE>
        """.trimIndent()
    }

    private fun table(map:Map<String,Node>): String {
        return """
            <TABLE>
            ${row(listOf("Key","Value"),"TH")}
            ${rows(map)}
            </TABLE>
        """.trimIndent()
    }

    private fun row(list:List<String>,type:String) : String {
        val out = StringBuilder("<TR>")
        for (e in list) {
            out.append("<$type>$e</$type>")
        }
        out.append("</TR>")
        return out.toString()
    }

    private fun rows(list:List<Node>): String {
        val rows = StringBuilder()
        for (item in list) {
            rows.append(row(nodeRenderer.render(item),"TD"))
        }
        return rows.toString()
    }

    private fun rows(map:Map<String,Node>): String {
        val rows = StringBuilder()
        for (entry in map) {
            val key = entry.key
            val value = entry.value.value.toString()
            val list = listOf(key,value)
            rows.append(row(list,"TD"))
        }
        return rows.toString()
    }

}