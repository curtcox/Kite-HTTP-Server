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
    private fun write(rawRequest: Request.Raw, out: OutputStream) {
        val request = Request.parse(rawRequest)
        val        body = handler.handle(request)
        val     headers = handler.handleHeaders(request,body)
        val    response = Response(body,headers)
        log(Transaction(request,response))
        writer.write(request.httpVersion,response,out)
    }

    private fun log(transaction: Transaction) {
        Log.log(SocketRequestHandler::class,transaction)
    }
}