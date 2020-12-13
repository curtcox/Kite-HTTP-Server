package mite.handlers

import mite.HTTPRequest
import mite.HTTPResponse
import mite.StatusCode
import java.util.function.Function

/**
 * A handler that uses a function to produce its responses.
 */
data class FunctionRequestHandler private constructor(val f: (HTTPRequest) -> String) :
    AbstractRequestHandler() {

    override fun handle(request: HTTPRequest): HTTPResponse {
        return HTTPResponse.of(f.invoke(request), StatusCode.OK)
    }

    companion object {
        fun of(f: (HTTPRequest) -> String): FunctionRequestHandler {
            return FunctionRequestHandler(f)
        }
    }
}