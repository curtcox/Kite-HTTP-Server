package mite.http

import mite.ast.Node
import mite.html.HTML
import java.io.*
import java.util.*

/**
 * This interface is essentially a namespace.
 * It doesn't define any methods, but groups a set of definitions about how we view the HTTP protocol
 * and what parts of it we support.
 */
interface HTTP {

    data class Transaction(val request: Request,val response: Response)

    /**
     * A header writer that is also a body handler.
     *
     * This could obviously be one interface with optional methods rather than
     * three different interfaces. This way seems more natural, since most things
     * will either provide a header or a body. There are, however, occasionally
     * things like authorization that will need to do both.
     */
    interface Handler : HeaderHandler {

        /**
         * Handle this request and produce a response.
         *
         * Note that this method may well be called again from a different
         * thread before it returns.  It is the responsibility of the implementer to
         * ensure that that doesn't cause any problems.
         */
        @Throws(IOException::class)
        fun handle(request: Request): Response.Body
    }

    /**
     * A header writer that is also a body handler.
     *
     * This could obviously be one interface with optional methods rather than
     * three different interfaces. This way seems more natural, since most things
     * will either provide a header or a body. There are, however, occasionally
     * things like authorization that will need to do both.
     */
    interface InternalHandler : BodyHandler, HeaderHandler

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
    interface BodyHandler : Request.Filter {
        /**
         * Handle this request and produce a response.
         *
         * Note that this method may well be called again from a different
         * thread before it returns.  It is the responsibility of the implementer to
         * ensure that that doesn't cause any problems.
         */
        @Throws(IOException::class)
        fun handle(request: Request): InternalResponse?
    }

    data class Header(val key:String, val value:Any)

    /**
     * For writing HTTP response headers.
     */
    interface HeaderHandler {

        fun handleHeaders(httpRequest: Request, response: Response.Body) : Array<Header>

    }

    /**
     * HTTP version.
     */
    data class Version private constructor(val version: String, val mimeAware: Boolean) {

        @Throws(IOException::class)
        fun writeHeaders(headers: Array<Header>, writer: Writer) {
            if (mimeAware) {
                for (header in headers) {
                    writeln("${header.key} ${header.value}", writer)
                }
                writeln("",writer)
                writer.flush()
            }
        }

        @Throws(IOException::class)
        private fun writeln(string: String, out: Writer) {
            out.write(string + "\r\n")
        }

        override fun toString(): String {
            return version
        }

        companion object {
            private const val UNKNOWN = "Unknown"
            val Unknown = Version(UNKNOWN, false)
            val _1_1 = Version("HTTP/1.1", true)
            fun fromRequest(request: String): Version {
                val versionString = versionString(request)
                return Version(versionString, versionString.startsWith("HTTP"))
            }

            private fun versionString(request: String): String {
                val st = StringTokenizer(request)
                st.nextToken() // we don't care about the method
                st.nextToken() // or what is being requested
                return if (st.hasMoreTokens()) {
                    st.nextToken()
                } else UNKNOWN
            }
        }
    }

    /**
     * The HTTP request sent to the server.
     * valid characters are ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~:/?#[]@!$&'()*+,;=
     */
    data class Request (
        val         raw: Raw, // The entire unparsed request string we were sent
        val      method: Method,
        val        host: String, // the server this request is for
        val    filename: String, // http://server/filename
        val contentType: ContentType,
        val httpVersion: Version
    ) {
        data class Raw (val lines:Array<String>)
        interface Filter {
            /**
             * Return true if this handler handles this request.
             */
            fun handles(request: Request): Boolean

        }
        enum class Method { GET, POST, UNKNOWN }
        companion object {
            fun parse(raw: Raw) = RequestParser.parse(raw)
        }

    }

    /**
     * The response to a HTTP request.
     */
    data class InternalResponse constructor(
        val payload: Any,
        val contentType: ContentType,
        val status: StatusCode,
        val renderer : Response.Body.Renderer? = null
    ) {
        interface Filter {
            /**
             * Return true if this renderer renders this response.
             */
            fun handles(request: Request,response:InternalResponse): Boolean

        }
        companion object {
            val noValidHandler = message("No valid handler",StatusCode.NOT_IMPLEMENTED)
            fun OK(payload: Any,contentType: ContentType) = InternalResponse(payload, contentType, StatusCode.OK)
            fun message(message: String, status: StatusCode) = InternalResponse(message, ContentType.TEXT, status)
            fun node(payload: Node,render:Response.Body.Renderer=Response.Body.TO_STRING) =
                InternalResponse(payload, ContentType.AST, StatusCode.OK, render)
        }
    }

    /**
     * The response to a HTTP request.
     */
    data class Response(val body:Body,val headers: Array<Header>) {
        data class Body constructor(val bytes: ByteArray, val contentType: ContentType, val status: StatusCode)
        {
            constructor(html: HTML, status: StatusCode) : this(html.toHtml().toByteArray(),ContentType.HTML,status)

            interface Renderer : InternalResponse.Filter {
                fun render(request: Request, internalResponse: InternalResponse) : Body
            }
            val page: String = String(bytes)
            abstract class UnconditionalRenderer : Renderer {
                override fun handles(request: Request, response: InternalResponse) = true
            }

            override fun toString() = "Body($contentType $status $page)"

            companion object {
                val TO_STRING = object : UnconditionalRenderer() {
                    override fun render(request: Request,inner: InternalResponse) =
                        Body(inner.toString().toByteArray(), ContentType.TEXT, inner.status)
                }

//                val empty = Body("".toByteArray(), ContentType.TEXT, StatusCode.OK)
//
//                fun bytes(bytes: ByteArray,contentType: ContentType) =
//                    Body(bytes, contentType, StatusCode.OK)

                fun OK(page: String = "", contentType: ContentType = ContentType.HTML) =
                    Body(page.toByteArray(), contentType, StatusCode.OK)
            }
        }
    }

    /**
     * Content AKA MIME types
     */
    enum class ContentType(val streamName: String, val binary:Boolean = true) {
        AST("internal/object",false),
        HTML("text/html; charset=utf-8",false),
        TEXT("text/plain",false),
        FORM_URLENCODED("application/x-www-form-urlencoded",false),
        ICON("image/x-icon"),
        JAVASCRIPT("text/javascript"),
        CSS("text/css"),
        GIF("image/gif"),
        CLASS("application/octet-stream"),
        JPEG("image/jpeg");
    }

    /**
     * HTTP status codes we support.
     */
    enum class StatusCode(
        /**
         * The message that will be returned to the client for this status
         */
        private val message: String
    ) {

        OK("200 OK"),
        UNAUTHORIZED("401 Unauthorized"),
        NOT_IMPLEMENTED("501 Not Implemented");

        override fun toString() = message
    }
}