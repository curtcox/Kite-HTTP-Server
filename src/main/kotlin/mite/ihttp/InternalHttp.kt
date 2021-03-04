package mite.ihttp

import mite.ast.Node
import mite.http.HTTP.*
import java.io.IOException

/**
 * This interface is essentially a namespace.
 */
interface  InternalHttp {

    interface BodyHandler : InternalRequest.Filter {
        @Throws(IOException::class)
        fun handle(request: InternalRequest): InternalResponse?
    }

    data class InternalRequest(val request:Request) {
        val filename = request.filename
        val method = request.method
        val host = request.host
        val httpVersion = request.httpVersion
        fun withoutPrefix(prefix: String) = InternalRequest(request.withoutPrefix(prefix))

        interface Filter {
            fun handles(request: InternalRequest): Boolean
        }
    }

    /**
     * The response to a HTTP request.
     */
    data class InternalResponse constructor(
        val payload: Any,               // the thing the request returns
        val status: StatusCode,         // how did the request go?
        val renderer : Renderer? = null // default way of turning this response into what the client gets
    ) {
        interface Filter {
            fun handles(request: InternalRequest, response:InternalResponse): Boolean
        }
        interface Renderer : Filter {
            fun render(request: InternalRequest, internalResponse: InternalResponse) : Response.Body
        }
        /**
         * Unconditional because it will render any response.
         */
        abstract class UnconditionalRenderer : Renderer {
            override fun handles(request: InternalRequest, response: InternalResponse) = true
        }


        companion object {
            val noValidHandler = message("No valid handler", StatusCode.NOT_IMPLEMENTED)
            private fun withContentType(contentType:ContentType) = object : UnconditionalRenderer() {
                override fun render(request: InternalRequest, response: InternalResponse) =
                    Response.Body(response.payload as ByteArray,contentType,response.status)
            }
            fun OK(payload: Any) = InternalResponse(payload, StatusCode.OK)
            fun OK(payload: Any,contentType:ContentType) = InternalResponse(payload, StatusCode.OK,withContentType(contentType))
            fun message(message: String, status: StatusCode) = InternalResponse(message, status)
            fun node(payload: Node, render: Renderer= Response.Body.TO_STRING) =
                InternalResponse(payload, StatusCode.OK, render)
        }
    }

}