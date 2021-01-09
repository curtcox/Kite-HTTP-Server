package mite.core

import java.io.*
import java.net.Socket

/**
 * A bridge between sockets and HTTP handlers.
 */
class SocketRequestHandler constructor(handler: HTTPHandler) {

    val handler = handler
    private val writer = HTTPResponseWriter

    @Throws(IOException::class)
    fun handle(request: Array<String>, socket: Socket, out: OutputStream) {
        socket.use {
            write(request,out)
        }
    }

    @Throws(IOException::class)
    private fun write(request: Array<String>, out: OutputStream) {
        val httpRequest = HTTPRequest.parse(request)
        val    response = handler.handle(httpRequest)!!
        val     headers = handler.handleHeaders(httpRequest,response)
        writer.write(httpRequest.httpVersion,response,headers,out)
    }

    fun handles(request: Array<String>): Boolean {
        return handler.handles(HTTPRequest.parse(request))
    }

}