package mite.bodies

import mite.ast.Node
import mite.http.HTTP.*

/**
 * A handler that uses a function to produce its responses.
 */
data class FunctionBodyHandler
    constructor(val prefix:String = "", val f: (Request) -> Node) : AbstractBodyHandler(prefix)
{

    override fun handle(request: Request): InternalResponse {
        return InternalResponse.node(f.invoke(request))
    }

}