package mite.bodies

import mite.core.HTTPRequest
import mite.util.HTML

/**
 * Simple handler mostly for demonstration and debugging.
 */
object EchoRequestHandler : HTML {
    fun of(): FunctionBodyHandler {
        return FunctionBodyHandler { request: HTTPRequest ->
            html(body(pre(
"""
request = $request
method  = ${request.method}
filename= ${request.filename}
""")))
        }
    }
}