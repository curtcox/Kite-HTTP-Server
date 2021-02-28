package mite.bodies

import mite.ast.*
import mite.ihttp.InternalHttp

/**
 * Simple handler mostly for demonstration and debugging.
 */
object EchoRequestHandler : AbstractAstNodeHandler("") {

    override fun root(request: InternalHttp.InternalRequest): Node = ReflectiveNode(mapOf(
        "request"  to request,
        "method"   to request.method,
        "version"  to request.httpVersion,
        "host"     to request.host,
        "filename" to request.filename
    ))

}