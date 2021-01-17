package mite.bodies

import mite.ast.Node
import mite.http.HTTP.Request
import mite.util.HTML

class FormBodyHandler : HTML {
    val handler = FunctionBodyHandler("",object: (Request) -> Node {
        val kind = EchoRequestHandler::class
        override fun invoke(request: Request) = Node.mapOfKind(kind,
            "request"  to request,
            "method"   to request.method,
            "version"  to request.httpVersion,
            "host"     to request.host,
            "filename" to request.filename
        )
    })
}