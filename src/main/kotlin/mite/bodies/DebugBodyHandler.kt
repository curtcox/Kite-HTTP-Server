package mite.bodies

import mite.ast.AbstractAstNodeHandler
import mite.ast.Node
import mite.ast.ReflectiveNode
import mite.http.HTTP.*
import mite.ihttp.InternalHttp.*

/**
 * For debugging routing problems.
 */
class DebugBodyHandler constructor(vararg handlers: BodyHandler) : AbstractAstNodeHandler("/?") {

    private val handlers = handlers as Array<BodyHandler>

    override fun root(request: InternalRequest): Node = ReflectiveNode(decisions(request))

    data class HandlerDecision(val handler:BodyHandler, val filename:String, val handles:Boolean)

    private fun decisions(request: InternalRequest) : List<HandlerDecision> {
        val list = mutableListOf<HandlerDecision>()
        val inner = request.withoutPrefix("/?")
        for (handler in handlers) {
            val handles = handler.handles(inner)
            list.add(HandlerDecision(handler,inner.filename,handles))
            if (handles) {
                return list
            }
        }
        return list
    }

}