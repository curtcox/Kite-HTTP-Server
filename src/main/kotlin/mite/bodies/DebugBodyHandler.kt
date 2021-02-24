package mite.bodies

import mite.ast.AbstractAstNodeHandler
import mite.ast.Node
import mite.ast.ReflectiveNode
import mite.http.HTTP.*

/**
 * For debugging routing problems.
 */
class DebugBodyHandler constructor(vararg handlers: BodyHandler) : AbstractAstNodeHandler("/?") {

    private val handlers = handlers as Array<BodyHandler>

    override fun root(request: Request): Node = ReflectiveNode(decisions(request))

    data class HandlerDecision(val handler:BodyHandler, val handles:Boolean)

    private fun decisions(request: Request) : List<HandlerDecision> {
        val list = mutableListOf<HandlerDecision>()
        for (handler in handlers) {
            val handles = handler.handles(request)
            list.add(HandlerDecision(handler,handles))
            if (handles) {
                return list
            }
        }
        return list
    }

}