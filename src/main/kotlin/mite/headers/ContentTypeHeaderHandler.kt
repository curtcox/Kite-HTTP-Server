package mite.headers

import mite.core.*
import mite.core.HTTP.*
import java.util.*

object ContentTypeHeaderHandler : HeaderHandler {

    override fun handleHeaders(httpRequest: Request, response: Response): Array<Header> {
        return arrayOf(
            Header("HTTP 1.0",         response.status),
            Header("Date:",            Date()),
            Header("Server:",          MiteHTTPServer.NAME),
            Header("Content-length:",  response.bytes.size),
            Header("Content-type:",    response.contentType.streamName),
        )
    }

}