package mite.core

import java.io.*
import java.net.Socket

/**
 * A bridge between sockets and HTTP handlers.
 */
class SocketRequestHandler private constructor(handler: HTTPHandler) {

    val handler = handler

    @Throws(IOException::class)
    fun handle(request: String, socket: Socket, out: OutputStream) {
        val httpRequest = HTTPRequest.parse(request)
        val writer: Writer = OutputStreamWriter(out)
        socket.use {
            writer.use {
                val response: HTTPResponse = handler.handle(httpRequest)!!
                handler.writeHeaders(httpRequest,response,writer)
                writer.write(response.page)
            }
        }
    }

    fun handles(request: String): Boolean {
        return handler.handles(HTTPRequest.parse(request))
    }

    companion object {
        fun of(handler: HTTPHandler): SocketRequestHandler {
            return SocketRequestHandler(handler)
        }
    }

}