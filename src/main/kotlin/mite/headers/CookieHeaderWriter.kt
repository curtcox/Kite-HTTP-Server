package mite.headers

import mite.core.HTTPHeaderWriter
import mite.core.HTTPRequest
import mite.core.HTTPResponse
import java.io.Writer

class CookieHeaderWriter : HTTPHeaderWriter {

    override fun writeHeaders(httpRequest: HTTPRequest, response: HTTPResponse, writer: Writer) {
        TODO("Not yet implemented")
    }
}