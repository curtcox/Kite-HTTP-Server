package mite.http

import mite.ast.Node
import java.io.*
import java.lang.Exception
import java.util.*

interface HTTP {

    /**
     * A header writer that is also a body handler.
     *
     * This could obviously be one interface with optional methods rather than
     * three different interfaces. This way seems more natural, since most things
     * will either provide a header or a body. There are, however, occasionally
     * things like authorization that will need to do both.
     */
//    interface Handler : HeaderHandler, BodyHandler
    interface Handler : HeaderHandler {

        /**
         * Handle this request and produce a response.
         *
         * Note that this method may well be called again from a different
         * thread before it returns.  It is the responsibility of the implementer to
         * ensure that that doesn't cause any problems.
         */
        @Throws(IOException::class)
        fun handle(request: Request): Response
    }

    /**
     * A header writer that is also a body handler.
     *
     * This could obviously be one interface with optional methods rather than
     * three different interfaces. This way seems more natural, since most things
     * will either provide a header or a body. There are, however, occasionally
     * things like authorization that will need to do both.
     */
//    interface Handler : HeaderHandler, BodyHandler
    interface InternalHandler : BodyHandler, HeaderHandler {

//        /**
//         * Handle this request and produce a response.
//         *
//         * Note that this method may well be called again from a different
//         * thread before it returns.  It is the responsibility of the implementer to
//         * ensure that that doesn't cause any problems.
//         */
//        @Throws(IOException::class)
//        fun handle(request: Request): InternalResponse?
    }


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

        fun handleHeaders(httpRequest: Request, response: Response) : Array<Header>

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
    data class Request constructor(
        val         raw: Array<String>, // The entire unparsed request string we were sent
        val      method: Method,
        val        host: String, // the server this request is for
        val    filename: String, // http://server/filename
        val contentType: ContentType,
        val httpVersion: Version
    ) {
        interface Filter {
            /**
             * Return true if this handler handles this request.
             */
            fun handles(request: Request): Boolean

        }
        enum class Method { GET, POST, UNKNOWN }
        companion object {
            private fun host(raw: Array<String>) = raw
                .filter { it.startsWith("Host:") }
                .map { x -> x.split(" ")[1] }
                .first()
            fun parse(raw: Array<String>): Request {
                return try {
                    val first = raw[0]
                    val tokenizer = StringTokenizer(first)
                    val method = when(tokenizer.nextToken()) {
                        "GET"  -> Method.GET
                        "POST" -> Method.POST
                        else   -> Method.UNKNOWN
                    }
                    val filename = tokenizer.nextToken()
                    Request(raw, method, host(raw), filename, ContentType.FORM_URLENCODED, Version.fromRequest(first))
                } catch (e: Exception) {
                    Request(raw, Method.UNKNOWN, "", "", ContentType.FORM_URLENCODED, Version.Unknown)
                }
            }
        }

    }

    /**
     * The response to a HTTP request.
     */
    data class InternalResponse constructor(
        val payload: Any,
        val contentType: ContentType,
        val status: StatusCode,
        val renderer : Response.Renderer = Response.TO_STRING
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
            fun node(payload: Node,render:Response.Renderer=Response.TO_STRING) =
                InternalResponse(payload, ContentType.AST, StatusCode.OK,render)
        }
    }

    /**
     * The response to a HTTP request.
     */
    data class Response constructor(
        val bytes: ByteArray, val contentType: ContentType, val status: StatusCode
    )
    {
        interface Renderer : InternalResponse.Filter {
            fun render(request: Request, internalResponse: InternalResponse) : Response
        }
        val page: String = String(bytes)
        abstract class UnconditionalRenderer : Renderer {
            override fun handles(request: Request, response: InternalResponse) = true
        }
        companion object {
            val TO_STRING = object : UnconditionalRenderer() {
                override fun render(request: Request,inner: InternalResponse) =
                    Response(inner.toString().toByteArray(),ContentType.TEXT,inner.status)
            }

            val empty = Response("".toByteArray(), ContentType.TEXT, StatusCode.OK)
//
//            fun of(page: String, contentType: ContentType, status: StatusCode) =
//                Response(page.toByteArray(), contentType, status)
//
            fun bytes(bytes: ByteArray,contentType: ContentType) =
                Response(bytes, contentType, StatusCode.OK)

            fun OK(page: String = "", contentType: ContentType = ContentType.HTML) =
                Response(page.toByteArray(), contentType, StatusCode.OK)
        }
    }

    /**
     * Content AKA MIME types
     */
    enum class ContentType(val streamName: String, val binary:Boolean = true) {

        HTML("text/html",false),
        TEXT("text/plain",false),
        ICON("image/x-icon"),
        AST("internal/object"),
        FORM_URLENCODED("application/x-www-form-urlencoded",false),
        GIF("image/gif"),
        CLASS("application/octet-stream"),
        JPEG("image/jpeg");

        companion object {
            fun auto(content:String) = if (seemsLikeHTML(content)) HTML else TEXT
            fun seemsLikeHTML(content:String) =
                content.length > 10 &&
                        content.substring(0,10).toLowerCase().contains("<html>")
        }
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