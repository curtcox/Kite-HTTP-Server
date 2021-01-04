package mite.handlers

import mite.core.*

class HandlerFromHeaderAndBody(val header: HTTPHeaderHandler, val body: HTTPBodyHandler) : HTTPHandler {

    override fun handleHeaders(httpRequest: HTTPRequest, response: HTTPResponse) =
        header.handleHeaders(httpRequest,response)

    override fun handles(request: HTTPRequest) = body.handles(request)

    override fun handle(request: HTTPRequest): HTTPResponse? = body.handle(request)
}