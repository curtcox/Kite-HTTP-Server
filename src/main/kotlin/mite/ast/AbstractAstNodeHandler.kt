package mite.ast

import mite.ast.Node.*
import mite.http.HTTP.*
import mite.renderers.HtmlRenderer
import mite.ihttp.InternalHttp.*

/**
 * Skeletal implementation of a handler that produces an AST node.
 * The implementor must implement node.
 * This base class will handle node tree navigation.
 */
abstract class AbstractAstNodeHandler(val prefix: String, val renderer:Response.Body.Renderer) : BodyHandler {

    constructor(prefix: String) : this(prefix,HtmlRenderer(ReflectionNodeRenderer))

    abstract fun root(request: Request): Node

    override fun handles(request: Request) = request.filename.startsWith(prefix)

    override fun handle(request: Request) = InternalResponse.node(node(request),renderer)

    private fun node(request: Request): Node {
        val parts = request.filename.split("@")
        if (parts.isEmpty()) {
            return root(request)
        }
        val steps = parts.subList(1,parts.size)
        return node(root(request),steps)
    }

    private fun node(node:Node, steps: List<String>): Node {
        if (steps.isEmpty()) {
            return node
        }
        val next = nodeStep(node,steps[0])
        if (steps.size==1) {
            return next
        }
        return node(next,steps.subList(1,steps.size))
    }

    private fun nodeStep(node:Node, step: String): Node {
        if (isIndexStep(node,step)) {
            return nodeAtIndex(node,step)
        }
        if (isKeyStep(node)) {
            return nodeAtKey(node,step)
        }
        throw IllegalArgumentException("$step not available for $node")
    }

    private fun isIndexStep(node: Node, part: String) = node.arity == Arity.list && part.toIntOrNull() != null
    private fun nodeAtIndex(node: Node, part: String) = node.list[part.toInt()]

    private fun isKeyStep(node: Node) = node.arity == Arity.map
    private fun nodeAtKey(node: Node, part: String) =
        if (node.map[part]==null) throw IllegalArgumentException("Missing $part")
        else node.map[part]!!

}