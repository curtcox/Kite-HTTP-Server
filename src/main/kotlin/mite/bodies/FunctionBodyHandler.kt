package mite.bodies

import mite.core.*

/**
 * A handler that uses a function to produce its responses.
 */
data class FunctionBodyHandler
    constructor(override val prefix:String = "", val f: (HTTPRequest) -> String) : AbstractBodyHandler(prefix)
{

    override fun handle(request: HTTPRequest): HTTPResponse {
        val content = f.invoke(request)
        return HTTPResponse.OK(content, ContentType.auto(content))
    }

}