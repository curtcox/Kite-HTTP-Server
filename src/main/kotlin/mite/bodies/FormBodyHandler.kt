package mite.bodies

import mite.ast.*
import mite.http.HTTP.Request
import mite.renderers.HTML

class FormBodyHandler : HTML {
    val handler = FunctionBodyHandler("",object: (Request) -> Node {
        val kind = EchoRequestHandler::class
        override fun invoke(request: Request) = SimpleNode.mapOfKind(kind,
            "request"  to request,
            "method"   to request.method,
            "version"  to request.httpVersion,
            "host"     to request.host,
            "filename" to request.filename
        )
    })
}