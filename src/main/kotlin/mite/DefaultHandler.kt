package mite

import mite.core.Log
import mite.http.HTTP.*
import mite.ihttp.InternalHttp.*

object DefaultHandler : Handler {

    val internalHandler = DefaultInternalHandler
    val responseRenderer = DefaultResponseRenderer
    val errorReporter = DefaultErrorReporter

    override fun handle(request: Request) :Response.Body {
        val inner = InternalRequest(request)
        var response :InternalResponse? = null
        return try {
            response = internalHandler.handle(inner)!!
            responseRenderer.render(inner,response)
        } catch (t:Throwable) {
            Log.log(DefaultHandler::class,t)
            errorReporter.render(inner,response,t)
        }
    }

}