package mite.bodies

import mite.core.*

/**
 * A handler that uses a function to produce its responses.
 */
data class FunctionBodyHandler
    private constructor(override val prefix:String, val f: (HTTPRequest) -> String) : AbstractBodyHandler(prefix)
{

    override fun handle(request: HTTPRequest): HTTPResponse {
        return HTTPResponse.of(f.invoke(request), StatusCode.OK)
    }

    companion object {
        fun of(prefix:String = "", f: (HTTPRequest) -> String): FunctionBodyHandler {
            return FunctionBodyHandler(prefix,f)
        }
    }
}