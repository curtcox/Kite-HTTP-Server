package mite.handlers

import mite.ihttp.InternalHttp.*

/**
 * Uses either one handler or the other depending on an on/off switch.
 */
class SwitchHandler
    constructor(
        val trueHandler: BodyHandler,
        val falseHandler: BodyHandler,
        val test: (InternalRequest)->Boolean): BodyHandler
{

    override fun handles(request: InternalRequest) =
        if (test(request)) trueHandler.handles(request)
        else              falseHandler.handles(request)

    override fun handle(request: InternalRequest) =
        if (test(request)) trueHandler.handle(request)
        else              falseHandler.handle(request)

}