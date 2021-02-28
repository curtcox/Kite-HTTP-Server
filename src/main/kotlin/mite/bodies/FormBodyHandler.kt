package mite.bodies

import mite.ast.*
import mite.ihttp.InternalHttp.*

class FormBodyHandler {
    val handler = FunctionBodyHandler("",object: (InternalRequest) -> Node {
        val kind = EchoRequestHandler::class
        override fun invoke(request: InternalRequest) = SimpleNode.mapOfKind(kind,
            "request"  to request,
            "method"   to request.method,
            "version"  to request.httpVersion,
            "host"     to request.host,
            "filename" to request.filename
        )
    })
}