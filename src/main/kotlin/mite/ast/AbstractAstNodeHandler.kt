package mite.ast

import mite.bodies.AbstractBodyHandler
import mite.http.HTTP.*

abstract class AbstractAstNodeHandler(prefix: String, val renderer:Response.Renderer) : AbstractBodyHandler(prefix) {

    abstract fun node(request: Request): Node

    final override fun handle(request: Request): InternalResponse {
        return InternalResponse.node(node(request), renderer)
    }

}