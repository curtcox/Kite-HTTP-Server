package mite.handlers

import mite.core.HTTP.*

/**
 * Uses either one handler or the other depending on an on/off switch.
 */
class SwitchHandler
    constructor(
        val trueHandler: Handler,
        val falseHandler: Handler,
        val test: (Request)->Boolean): Handler
{
    override fun handleHeaders(request: Request, response: Response) =
        if (test(request)) trueHandler.handleHeaders(request,response)
        else              falseHandler.handleHeaders(request,response)


    override fun handles(request: Request) =
        if (test(request)) trueHandler.handles(request)
        else              falseHandler.handles(request)

    override fun handle(request: Request) =
        if (test(request)) trueHandler.handle(request)
        else              falseHandler.handle(request)

}