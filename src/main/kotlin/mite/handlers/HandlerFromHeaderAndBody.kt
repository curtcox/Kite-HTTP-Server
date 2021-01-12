package mite.handlers

import mite.core.HTTP.*

class HandlerFromHeaderAndBody(val header: HeaderHandler, val body: BodyHandler) : Handler {

    override fun handleHeaders(httpRequest: Request, response: Response) =
        header.handleHeaders(httpRequest,response)

    override fun handles(request: Request) = body.handles(request)

    override fun handle(request: Request): Response? = body.handle(request)
}