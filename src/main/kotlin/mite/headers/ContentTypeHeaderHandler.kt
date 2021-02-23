package mite.headers

import mite.core.*
import mite.http.HTTP.*
import java.util.*

object ContentTypeHeaderHandler : HeaderHandler {

    override fun handleHeaders(httpRequest: Request, response: Response.Body): Array<Header> {
        return arrayOf(
            Header("HTTP/1.0",         response.status),
            Header("Server:",          MiteHTTPServer.NAME),
            Header("Content-Type:",    response.contentType.streamName),
            Header("Date:",            Date()),
            Header("Content-Length:",  response.bytes.size),
        )
    }

}