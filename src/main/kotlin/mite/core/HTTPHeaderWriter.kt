package mite.core

import java.io.Writer

interface HTTPHeaderWriter {

  fun writeHeaders(httpRequest: HTTPRequest, response: HTTPResponse, writer: Writer)

}