package mite.headers

import mite.http.HTTP.*

object LoginCookie {

    fun handleHeaders(httpRequest: Request, response: Response): Array<Header> {
        return emptyArray()
    }

    fun isLoggedIn(httpRequest: Request) : Boolean {
        return true
    }

}