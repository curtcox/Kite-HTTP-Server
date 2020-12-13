package mite.handlers

import mite.HTTPRequest

/**
 * Simple handler mostly for demonstration and debugging.
 */
object EchoRequestHandler {
    private val R = "\r"
    fun of(): FunctionRequestHandler {
        return FunctionRequestHandler.of { request:HTTPRequest ->
            "<html>" +
                    "<body>" +
                    "<pre>" +
                    "request =" + request + R +
                    "method  =" + request.method + R +
                    "filename=" + request.filename + R +
                    "</pre>" +
                    "</body>" +
                    "</html>"
        }
    }
}