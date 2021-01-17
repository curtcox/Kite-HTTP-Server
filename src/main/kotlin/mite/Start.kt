package mite

import mite.core.*
import mite.http.HTTP.*
import java.io.IOException

/**
 * Configure and start the server.
 */
object Start {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val h = DefaultHandler
        val r = object: Response.Renderer {
            override fun render(internalResponse: InternalResponse): Response {
                TODO("Not yet implemented")
            }

        }
        MiteHTTPServer.startListeningOnPort(8000, object: Handler {
            override fun handle(request: Request): Response {
                return r.render(h.handle(request)!!)
            }

            override fun handleHeaders(httpRequest: Request, response: Response): Array<Header> {
                return h.handleHeaders(httpRequest,response)
            }

        })
    }
}