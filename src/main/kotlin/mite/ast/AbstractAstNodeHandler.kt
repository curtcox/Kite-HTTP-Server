package mite.ast

import mite.ast.Node.*
import mite.bodies.AbstractBodyHandler
import mite.http.HTTP.*
import mite.renderers.HtmlRenderer

/**
 * Skeletal implementation of a handler that produces an AST node.
 * The implementor must implement node.
 * This base class will handle node tree navigation.
 */
abstract class AbstractAstNodeHandler(prefix: String, val renderer:Response.Body.Renderer) : AbstractBodyHandler(prefix) {

    constructor(prefix: String) : this(prefix,HtmlRenderer(ReflectionRenderer))

    abstract fun root(): Node

    final override fun handle(request: Request): InternalResponse {
        return InternalResponse.node(node(request),renderer)
    }

    private fun node(request: Request): Node {
        val parts = request.filename.split("@")
        if (parts.isEmpty()) {
            return root()
        }
        val steps = parts.subList(1,parts.size)
        return node(root(),steps)
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
        if (isKeyStep(node,step)) {
            return nodeAtKey(node,step)
        }
        throw IllegalArgumentException("$step not in $node")
    }

    private fun isIndexStep(node: Node, part: String) = node.arity == Arity.list && part.toIntOrNull() != null
    private fun nodeAtIndex(node: Node, part: String) = node.list[part.toInt()]

    private fun isKeyStep(node: Node, part: String) = node.arity == Arity.map && part != null
    private fun nodeAtKey(node: Node, part: String) =
        if (node.map[part]==null) throw IllegalArgumentException("Missing $part")
        else node.map[part]!!

}