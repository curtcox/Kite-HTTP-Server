package mite.handlers

import mite.HTTPRequest
import mite.HTTPResponse
import mite.StatusCode
import java.util.function.Function

/**
 * A handler that uses a function to produce its responses.
 */
class FunctionRequestHandler private constructor(val f: RequestToString) :
    AbstractRequestHandler() {
    interface RequestToString : Function<HTTPRequest, String>

    override fun handle(request: HTTPRequest): HTTPResponse {
        return HTTPResponse.of(f.apply(request), StatusCode.OK)
    }

    companion object {
        fun of(f: RequestToString): FunctionRequestHandler {
            return FunctionRequestHandler(f)
        }
    }
}