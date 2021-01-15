package mite.bodies

import mite.ast.Node
import mite.http.HTTP.*

/**
 * A handler that uses a function to produce its responses.
 */
data class FunctionBodyHandler
    constructor(val prefix:String = "", val f: (Request) -> Node, val g: (Node) -> String) : AbstractBodyHandler(prefix)
{

    override fun handle(request: Request): Response {
        val content = g.invoke(f.invoke(request))
        return Response.OK(content, ContentType.auto(content))
    }

}