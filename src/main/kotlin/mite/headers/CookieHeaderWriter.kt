package mite.headers

import mite.HTTPHeaderWriter
import mite.HTTPRequest
import mite.HTTPResponse
import java.io.Writer

class CookieHeaderWriter : HTTPHeaderWriter {

    override fun writeHeaders(httpRequest: HTTPRequest, response: HTTPResponse, writer: Writer) {
        TODO("Not yet implemented")
    }
}