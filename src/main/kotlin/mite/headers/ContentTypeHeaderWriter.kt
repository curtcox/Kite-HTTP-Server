package mite.headers

import mite.core.*
import java.io.*

class ContentTypeHeaderWriter : HTTPHeaderWriter {

    override fun writeHeaders(httpRequest: HTTPRequest, response: HTTPResponse, writer: Writer) {
        val page: String = response.page
        if (httpRequest.httpVersion.mimeAware) {
            response.contentType.writeMIMEHeader(writer, response.status, page.length)
        }
    }
}