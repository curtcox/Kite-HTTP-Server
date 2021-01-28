package mite.core

import java.io.*
import java.net.Socket
import mite.http.HTTP.*

/**
 * A bridge between sockets and HTTP handlers.
 */
class SocketRequestHandler constructor(handler: Handler) {

    val handler = handler
    private val writer = HTTPResponseWriter

    @Throws(IOException::class)
    fun handle(request: Request.Raw, socket: Socket, out: OutputStream) {
        socket.use {
            write(request,out)
        }
    }

    @Throws(IOException::class)
    private fun write(request: Request.Raw, out: OutputStream) {
        val httpRequest = Request.parse(request)
        val    response = handler.handle(httpRequest)
        val     headers = handler.handleHeaders(httpRequest,response)
        writer.write(httpRequest.httpVersion,response,headers,out)
    }

}