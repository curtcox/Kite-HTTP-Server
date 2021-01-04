package mite.core

/**
 * For writing HTTP response headers.
 */
interface HTTPHeaderHandler {

  fun handleHeaders(httpRequest: HTTPRequest, response: HTTPResponse) : Array<HTTPHeader>

}