package mite.core

import mite.core.StatusCode.*
import mite.core.ContentType.*

/**
 * The response to a HTTP request.
 */
data class HTTPResponse private constructor(
    val bytes: ByteArray, val contentType: ContentType, val status: StatusCode)
{
    val page: String = bytes.toString()

    companion object {
        val empty = HTTPResponse("".toByteArray(), TEXT, OK)

        fun of(page: String, contentType: ContentType, status: StatusCode) =
            HTTPResponse(page.toByteArray(), contentType, status)

        fun bytes(bytes: ByteArray,contentType: ContentType) = HTTPResponse(bytes, contentType, OK)

        fun OK(page: String = "", contentType: ContentType = HTML) = HTTPResponse(page.toByteArray(), contentType, OK)
    }
}