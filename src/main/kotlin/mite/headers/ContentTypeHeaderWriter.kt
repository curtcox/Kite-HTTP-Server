package mite.headers

import mite.core.*
import java.io.*

class ContentTypeHeaderWriter : HTTPHeaderWriter {

    override fun writeHeaders(httpRequest: HTTPRequest, response: HTTPResponse, writer: Writer) {
        val page: String = response.page
        val version = httpRequest.httpVersion
        version.writeHeaders(writer, response.status, page.length, response.contentType)
    }
}