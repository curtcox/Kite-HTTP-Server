package mite.bodies

import mite.ast.Node
import mite.http.HTTP.Request

/**
 * Simple handler mostly for demonstration and debugging.
 */
object EchoRequestHandler {

    fun of(g:(Node)->String) = FunctionBodyHandler("",object: (Request) -> Node {
        val kind = EchoRequestHandler::class
        override fun invoke(request: Request) = Node.map(kind,mapOf(
                "request"  to request,
                "method"   to request.method,
                "version"  to request.httpVersion,
                "host"     to request.host,
                "filename" to request.filename
            ))
    },g)
}