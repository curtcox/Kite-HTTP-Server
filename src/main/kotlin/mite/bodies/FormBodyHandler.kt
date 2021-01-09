package mite.bodies

import mite.core.*
import mite.util.HTML

class FormBodyHandler : HTML {
    fun of() = FunctionBodyHandler { request: HTTPRequest ->
        html(body(pre("""
            request = $request
            method  = ${request.method}
            filename= ${request.filename}
            """.trimIndent())))
    }
}