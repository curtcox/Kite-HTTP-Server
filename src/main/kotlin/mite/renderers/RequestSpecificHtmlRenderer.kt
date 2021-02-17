package mite.renderers

import mite.ast.Node
import mite.html.Table
import mite.http.HTTP

class RequestSpecificHtmlRenderer(val request: HTTP.Request, val nodeRenderer: Node.Renderer) {

    fun node(node: Node): Table {
        return when (node.arity) {
            (Node.Arity.leaf) -> table(listOf(node))
            (Node.Arity.list) -> table(node.list)
            (Node.Arity.map)  -> table(node.map)
            else -> TODO("Not yet implemented")
        }
    }

    private fun table(list:List<Node>)     = Table(row(nodeRenderer.header(),"TH"),rows(list))

    private fun table(map:Map<String, Node>) = Table(row(listOf("Key","Value"),"TH"),rows(map))

    private fun row(list:List<String>,type:String) : String {
        val out = StringBuilder()
        for (e in list) {
            out.append("<$type>$e</$type>")
        }
        return "<TR>$out</TR>"
    }

    private fun rows(list:List<Node>): String {
        val rows = StringBuilder()
        for (item in list) {
            rows.append(row(nodeRenderer.render(item),"TD"))
        }
        return rows.toString()
    }

    private fun rows(map:Map<String, Node>): String {
        val rows = StringBuilder()
        for (entry in map) {
            val key = linkTo(entry.key)
            val value = entry.value.value.toString()
            val list = listOf(key,value)
            rows.append(row(list,"TD"))
        }
        return rows.toString()
    }

    private fun linkTo(text:String) = """<a href="${request.filename}@$text">$text</a>"""
}
