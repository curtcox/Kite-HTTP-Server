package mite.core

import java.io.*
import java.net.Socket

/**
 * A bridge between sockets and HTTP handlers.
 */
class SocketRequestHandler constructor(handler: HTTPHandler) {

    val handler = handler

    @Throws(IOException::class)
    fun handle(request: String, socket: Socket, out: OutputStream) {
        socket.use {
            val httpRequest = HTTPRequest.parse(request)
            val response: HTTPResponse = handler.handle(httpRequest)!!
            val writer: Writer = OutputStreamWriter(out)
            writer.use {
                handler.writeHeaders(httpRequest,response,writer)
                if (response.contentType.binary)
                    out.write(response.bytes)
                else
                    writer.write(response.page)
            }
        }
    }

    fun handles(request: String): Boolean {
        return handler.handles(HTTPRequest.parse(request))
    }

}