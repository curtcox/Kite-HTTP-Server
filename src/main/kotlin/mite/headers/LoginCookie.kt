package mite.headers

import mite.core.*

object LoginCookie {

    fun handleHeaders(httpRequest: HTTPRequest, response: HTTPResponse): Array<HTTPHeader> {
        return emptyArray()
    }

    fun isLoggedIn(httpRequest: HTTPRequest) : Boolean {
        return true
    }

}