package mite.renderers

import mite.ast.Node
import mite.html.Table
import mite.html.Table.*
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

    private fun table(list:List<Node>) = Table(Row(listOf("#") + nodeRenderer.header(list), "TH"),Body(rows(list)))

    private fun table(map:Map<String, Node>) = Table(Row(listOf("Key","Value"),"TH"),Body(rows(map)))

    private fun rows(list:List<Node>) = list.mapIndexed {
        i, n -> Row(listOf(linkTo(i.toString())) + nodeRenderer.render(n),"TD")
    }

    private fun rows(map:Map<String, Node>) =
        map.entries.map { e ->
            val key = linkTo(e.key)
            val value = e.value.value.toString()
            val list = listOf(key,value)
            Row(list,"TD")
        }

    private fun linkTo(text:String) = """<a href="${request.filename}@$text">$text</a>"""
}
