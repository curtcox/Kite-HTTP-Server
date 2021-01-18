package mite

import mite.http.HTTP.*

object DefaultHandler : Handler {

    val internalHandler = DefaultInternalHandler
    val responseRenderer = DefaultResponseRenderer

    override fun handle(request: Request) =
        responseRenderer.render(internalHandler.handle(request)!!)

    override fun handleHeaders(httpRequest: Request, response: Response) =
        internalHandler.handleHeaders(httpRequest,response)

}