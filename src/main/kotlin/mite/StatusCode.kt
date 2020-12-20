package mite

/**
 * HTTP status codes we support.
 */
enum class StatusCode(
    /**
     * The message that will be returned to the client for this status
     */
    private val message: String
) {

    OK("200 OK"),
    UNAUTHORIZED("401 Unauthorized"),
    NOT_IMPLEMENTED("501 Not Implemented");

    override fun toString() = message
}