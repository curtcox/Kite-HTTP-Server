package mite

import java.io.IOException
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.Writer
import java.net.Socket

class SocketRequestHandler private constructor(handler: HTTPRequestHandler) {

    val handler: HTTPRequestHandler

    @Throws(IOException::class)
    fun handle(request: String, socket: Socket, out: OutputStream) {
        val httpRequest = HTTPRequest.parse(request)
        val writer: Writer = OutputStreamWriter(out)
        val response: HTTPResponse = handler.handle(httpRequest)
        val page: String = response.page
        if (httpRequest.httpVersion.mimeAware) {
            ContentType.HTML.writeMIMEHeader(writer, response.status, page.length)
        }
        writer.write(page)
        writer.close()
        socket.close()
    }

    fun handles(request: String): Boolean {
        return handler.handles(HTTPRequest.parse(request))
    }

    companion object {
        fun of(handler: HTTPRequestHandler): SocketRequestHandler {
            return SocketRequestHandler(handler)
        }
    }

    init {
        this.handler = handler
    }
}