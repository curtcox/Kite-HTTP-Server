package mite.handlers

import mite.http.HTTP.*
import mite.ihttp.InternalHttp.*

/**
 * Uses either one handler or the other depending on an on/off switch.
 */
class SwitchHandler
    constructor(
        val trueHandler: InternalHandler,
        val falseHandler: InternalHandler,
        val test: (Request)->Boolean): InternalHandler
{
    override fun handleHeaders(request: Request, response: Response.Body) =
        if (test(request)) trueHandler.handleHeaders(request,response)
        else              falseHandler.handleHeaders(request,response)


    override fun handles(request: Request) =
        if (test(request)) trueHandler.handles(request)
        else              falseHandler.handles(request)

    override fun handle(request: Request) =
        if (test(request)) trueHandler.handle(request)
        else              falseHandler.handle(request)

}