package mite

class HTTPResponse private constructor(val page: String, val status: StatusCode) {
    companion object {
        val empty = HTTPResponse("",StatusCode.OK)
        fun of(page: String, status: StatusCode): HTTPResponse {
            return HTTPResponse(page, status)
        }
    }
}