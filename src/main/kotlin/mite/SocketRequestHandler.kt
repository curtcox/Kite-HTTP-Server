package mite

import java.io.*
import java.net.Socket

class SocketRequestHandler private constructor(handler: HTTPRequestHandler, headerWriter: HTTPHeaderWriter) {

    val handler: HTTPRequestHandler = handler
    val headerWriter: HTTPHeaderWriter = headerWriter

    @Throws(IOException::class)
    fun handle(request: String, socket: Socket, out: OutputStream) {
        val httpRequest = HTTPRequest.parse(request)
        val writer: Writer = OutputStreamWriter(out)
        socket.use {
            writer.use {
                val response: HTTPResponse = handler.handle(httpRequest)!!
                headerWriter.writeHeaders(httpRequest,response,writer)
                writer.write(response.page)
            }
        }
    }

    fun handles(request: String): Boolean {
        return handler.handles(HTTPRequest.parse(request))
    }

    companion object {
        fun of(handler: HTTPRequestHandler,headerWriter: HTTPHeaderWriter): SocketRequestHandler {
            return SocketRequestHandler(handler,headerWriter)
        }
    }

}