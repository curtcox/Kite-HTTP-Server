package mite.ast

import mite.bodies.AbstractBodyHandler
import mite.http.HTTP.*
import mite.renderers.HtmlRenderer
import kotlin.reflect.KClass

/**
 * Skeletal implementation of a handler that produces an AST node.
 * The implementor must implement node.
 * This base class will handle node tree navigation.
 */
abstract class AbstractAstNodeHandler(prefix: String, val renderer:Response.Renderer) : AbstractBodyHandler(prefix) {

    constructor(prefix: String,kind: KClass<*>) : this(prefix,HtmlRenderer(ReflectionRenderer(kind)))

    abstract fun node(request: Request): Node

    final override fun handle(request: Request): InternalResponse {
        return InternalResponse.node(node(request), renderer)
    }

}