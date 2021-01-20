package mite.renderers

import mite.ast.Node
import mite.core.Log
import mite.http.HTTP.*

class HtmlRenderer() : Response.UnconditionalRenderer(), HTML {

    override fun render(request: Request, response: InternalResponse): Response {
        if (response.contentType == ContentType.AST) {
            val s = html(node(response.payload as Node))
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
            <TR><TH>Time</TH><TH>Log</TH><TH>Record</TH><TH>Stack</TH></TR>
            ${rows(list)}
            </TABLE>
        """.trimIndent()
    }

    private fun rows(list:List<Node>): String {
        val rows = StringBuilder()
        for (item in list) {
            val entry = item as Log.Entry
            rows.append(
                "<TR><TD>${entry.time}</TD><TD>${entry.logger}</TD><TD>${entry.record}</TD><TD>${entry.stack}</TD></TR>")
        }
        return rows.toString()
    }

}