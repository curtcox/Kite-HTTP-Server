package mite.bodies

import mite.ast.Node
import mite.http.HTTP.*
import mite.http.HTTP.Response.*
import mite.http.HTTP.Response.Body.*

/**
 * A handler that uses a function to produce its responses.
 */
data class FunctionBodyHandler
    constructor(
        val prefix:String = "",
        val f: (Request) -> Node,
        val render:Renderer=Body.TO_STRING
    ) : AbstractBodyHandler(prefix)
{
    override fun handle(request: Request) = InternalResponse.node(f.invoke(request))
}