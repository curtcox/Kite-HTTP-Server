package mite.headers

import mite.http.HTTP.*
import mite.ihttp.InternalHttp.*

object LoginCookie {

    fun handleHeaders(httpRequest: InternalRequest, response: Response.Body): Array<Header> {
        return emptyArray()
    }

    fun isLoggedIn(httpRequest: InternalRequest) : Boolean {
        return true
    }

}