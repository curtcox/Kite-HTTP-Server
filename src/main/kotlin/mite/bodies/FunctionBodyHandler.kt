package mite.bodies

import mite.ast.Node
import mite.http.HTTP.Response.*
import mite.ihttp.InternalHttp.*

/**
 * A handler that uses a function to produce its responses.
 */
data class FunctionBodyHandler
    constructor(
        val prefix:String = "",
        val f: (InternalRequest) -> Node,
        val render:InternalResponse.Renderer=Body.TO_STRING
    ) : AbstractBodyHandler(prefix)
{
    override fun handle(request: InternalRequest) = InternalResponse.node(f.invoke(request))
}