package mite.bodies

import mite.http.HTTP.*

/**
 * A handler that uses a function to produce its responses.
 */
data class FunctionBodyHandler
    constructor(val prefix:String = "", val f: (Request) -> String) : AbstractBodyHandler(prefix)
{

    override fun handle(request: Request): Response {
        val content = f.invoke(request)
        return Response.OK(content, ContentType.auto(content))
    }

}