package mite.handlers

import mite.core.*

/**
 * Uses either one handler or the other depending on a on/off switch.
 */
class SwitchHandler
    constructor(
        val trueHandler: HTTPHandler,
        val falseHandler: HTTPHandler,
        val test: (HTTPRequest)->Boolean): HTTPHandler
{
    override fun handleHeaders(request: HTTPRequest, response: HTTPResponse) =
        if (test(request)) trueHandler.handleHeaders(request,response)
        else              falseHandler.handleHeaders(request,response)


    override fun handles(request: HTTPRequest) =
        if (test(request)) trueHandler.handles(request)
        else              falseHandler.handles(request)

    override fun handle(request: HTTPRequest) =
        if (test(request)) trueHandler.handle(request)
        else              falseHandler.handle(request)

}