package mite.handlers

import mite.ihttp.InternalHttp.*

/**
 * Translates GET requests into analogous POST request.
 * This allows posts to be passed around as URLs, but can obviously trash
 * GET semantic guarantees.
 */
class PostHandler constructor(val handler: BodyHandler) : BodyHandler
{
    private val POST = "/POST/"

    override fun handles(request: InternalRequest) = request.filename.startsWith(POST)

    override fun handle(request: InternalRequest) = handler.handle(request)

}