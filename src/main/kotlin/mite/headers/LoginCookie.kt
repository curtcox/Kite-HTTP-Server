package mite.headers

import mite.core.HTTP.*

object LoginCookie {

    fun handleHeaders(httpRequest: Request, response: Response): Array<Header> {
        return emptyArray()
    }

    fun isLoggedIn(httpRequest: Request) : Boolean {
        return true
    }

}