package mite.core

import java.io.Writer

/**
 * For writing HTTP response headers.
 */
interface HTTPHeaderWriter {

  fun writeHeaders(httpRequest: HTTPRequest, response: HTTPResponse, writer: Writer)

}