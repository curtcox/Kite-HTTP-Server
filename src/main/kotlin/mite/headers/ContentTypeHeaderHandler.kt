package mite.headers

import mite.core.*
import java.util.*

object ContentTypeHeaderHandler : HTTPHeaderHandler {

    override fun handleHeaders(httpRequest: HTTPRequest, response: HTTPResponse): Array<HTTPHeader> {
        return arrayOf(
            HTTPHeader("HTTP 1.0",         response.status),
            HTTPHeader("Date:",            Date()),
            HTTPHeader("Server:",          MiteHTTPServer.NAME),
            HTTPHeader("Content-length:",  response.bytes.size),
            HTTPHeader("Content-type:",    response.contentType.streamName),
        )
    }

}