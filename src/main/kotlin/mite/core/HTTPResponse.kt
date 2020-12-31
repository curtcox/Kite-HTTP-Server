package mite.core

import mite.core.StatusCode.*
import mite.core.ContentType.*

/**
 * The response to a HTTP request.
 */
data class HTTPResponse private constructor(
    val page: String, val contentType: ContentType, val status: StatusCode)
{
    companion object {
        val empty = HTTPResponse("", HTML, OK)
        fun of(page: String, contentType: ContentType, status: StatusCode): HTTPResponse {
            return HTTPResponse(page, contentType, status)
        }
        fun OK(page: String = "", contentType: ContentType = HTML): HTTPResponse {
            return HTTPResponse(page, contentType, OK)
        }
    }
}