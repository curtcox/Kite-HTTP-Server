package mite.handlers

import mite.core.HTTP.*

/**
 * Translates GET requests into analogous POST request.
 * This allows posts to be passed around as URLs, but can obviously trash
 * GET semantic guarantees.
 */
class PostHandler constructor(val handler: Handler) : Handler
{
    private val POST = "/POST/"

    override fun handleHeaders(request: Request, response: Response) =
        handler.handleHeaders(request,response)

    override fun handles(request: Request) = request.filename.startsWith(POST)

    override fun handle(request: Request) = handler.handle(request)

}