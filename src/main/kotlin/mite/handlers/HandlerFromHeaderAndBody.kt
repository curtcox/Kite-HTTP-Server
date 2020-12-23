package mite.handlers

import mite.core.*
import java.io.Writer

class HandlerFromHeaderAndBody(val header: HTTPHeaderWriter, val body: HTTPBodyHandler) : HTTPHandler {

    override fun writeHeaders(httpRequest: HTTPRequest, response: HTTPResponse, writer: Writer) {
        header.writeHeaders(httpRequest,response,writer)
    }

    override fun handles(request: HTTPRequest) = body.handles(request)

    override fun handle(request: HTTPRequest): HTTPResponse? = body.handle(request)
}