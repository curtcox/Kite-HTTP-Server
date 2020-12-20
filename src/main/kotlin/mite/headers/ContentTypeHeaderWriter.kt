package mite.headers

import mite.*
import java.io.*

class ContentTypeHeaderWriter : HTTPHeaderWriter {

    override fun writeHeaders(httpRequest: HTTPRequest, response: HTTPResponse, writer: Writer) {
        val page: String = response.page
        if (httpRequest.httpVersion.mimeAware) {
            ContentType.HTML.writeMIMEHeader(writer, response.status, page.length)
        }
    }
}