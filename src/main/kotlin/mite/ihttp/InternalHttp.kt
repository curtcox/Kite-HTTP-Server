package mite.ihttp

import mite.ast.Node
import mite.http.HTTP
import java.io.IOException

interface  InternalHttp {
    /**
     * A header writer that is also a body handler.
     *
     * This could obviously be one interface with optional methods rather than
     * three different interfaces. This way seems more natural, since most things
     * will either provide a header or a body. There are, however, occasionally
     * things like authorization that will need to do both.
     */
    interface InternalHandler : BodyHandler, HTTP.HeaderHandler

    /**
     * This interface is used to define what an HTTP Server does.
     *
     * The intent is to allow groups of handlers to be composed and treated as a single handler.
     * See CompositeRequestHandler for a simple example of this concept.
     * Note that the expectation is subtly different for the root HTTPRequestHandler and component
     * HTTPHandlerS because the root has nothing to delegate to if it doesn't produce a response.
     *
     * Implementors may want to use AbstractRequestHandler, so that they only need implement handle.
     */
    interface BodyHandler : HTTP.Request.Filter {
        /**
         * Handle this request and produce a response.
         *
         * Note that this method may well be called again from a different
         * thread before it returns.  It is the responsibility of the implementer to
         * ensure that that doesn't cause any problems.
         */
        @Throws(IOException::class)
        fun handle(request: HTTP.Request): InternalResponse?
    }

    /**
     * The response to a HTTP request.
     */
    data class InternalResponse constructor(
        val payload: Any,
        val contentType: HTTP.ContentType,
        val status: HTTP.StatusCode,
        val renderer : HTTP.Response.Body.Renderer? = null
    ) {
        interface Filter {
            /**
             * Return true if this renderer renders this response.
             */
            fun handles(request: HTTP.Request, response:InternalResponse): Boolean

        }
        companion object {
            val noValidHandler = message("No valid handler", HTTP.StatusCode.NOT_IMPLEMENTED)
            fun OK(payload: Any,contentType: HTTP.ContentType) = InternalResponse(payload, contentType, HTTP.StatusCode.OK)
            fun message(message: String, status: HTTP.StatusCode) = InternalResponse(message, HTTP.ContentType.TEXT, status)
            fun node(payload: Node, render: HTTP.Response.Body.Renderer= HTTP.Response.Body.TO_STRING) =
                InternalResponse(payload, HTTP.ContentType.AST, HTTP.StatusCode.OK, render)
        }
    }

}