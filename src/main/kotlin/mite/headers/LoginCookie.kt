package mite.headers

import mite.core.*
import java.io.Writer

object LoginCookie {

    fun writeHeaders(httpRequest: HTTPRequest, response: HTTPResponse, writer: Writer) {

    }

    fun isLoggedIn(httpRequest: HTTPRequest) : Boolean {
        return true
    }

}