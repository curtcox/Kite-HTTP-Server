package mite.bodies

import mite.core.*

/**
 * A handler that uses a function to produce its responses.
 */
data class FunctionBodyHandler private constructor(val f: (HTTPRequest) -> String) :
    AbstractBodyHandler()
{

    override fun handle(request: HTTPRequest): HTTPResponse {
        return HTTPResponse.of(f.invoke(request), StatusCode.OK)
    }

    companion object {
        fun of(f: (HTTPRequest) -> String): FunctionBodyHandler {
            return FunctionBodyHandler(f)
        }
    }
}