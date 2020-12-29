package mite.handlers

import mite.core.*
import java.io.Writer

/**
 * Uses either one handler or the other depending on a on/off switch.
 */
class SwitchHandler
    constructor(
        val trueHandler: HTTPHandler,
        val falseHandler: HTTPHandler,
        val test: (HTTPRequest)->Boolean): HTTPHandler
{
    override fun writeHeaders(request: HTTPRequest, response: HTTPResponse, writer: Writer) {
        if (test(request))
            trueHandler.writeHeaders(request,response,writer)
        else
            falseHandler.writeHeaders(request,response,writer)
    }

    override fun handles(request: HTTPRequest) =
        if (test(request)) trueHandler.handles(request) else falseHandler.handles(request)

    override fun handle(request: HTTPRequest) =
        if (test(request)) trueHandler.handle(request) else falseHandler.handle(request)

}