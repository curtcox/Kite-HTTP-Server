package mite.handlers

import mite.core.*

/**
 * Translates GET requests into analogous POST request.
 * This allows posts to be passed around as URLs, but can obviously trash
 * GET semantic guarantees.
 */
class PostHandler constructor(val handler: HTTPHandler) : HTTPHandler
{
    private val POST = "/POST/"

    override fun handleHeaders(request: HTTPRequest, response: HTTPResponse) =
        handler.handleHeaders(request,response)

    override fun handles(request: HTTPRequest) = request.filename.startsWith(POST)

    override fun handle(request: HTTPRequest) = handler.handle(request)

}