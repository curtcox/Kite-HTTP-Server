package mite.headers

import mite.*
import java.io.Writer

class CompositeHeaderWriter private constructor(vararg headers: HTTPHeaderWriter) : HTTPHeaderWriter {

    private val headers = headers as Array<HTTPHeaderWriter>

    override fun writeHeaders(httpRequest: HTTPRequest, response: HTTPResponse, writer: Writer) {
        for (header in headers) {
            header.writeHeaders(httpRequest,response,writer)
        }
    }

    companion object {
        fun of(vararg headers: HTTPHeaderWriter): CompositeHeaderWriter {
            return CompositeHeaderWriter(*headers)
        }
    }

}