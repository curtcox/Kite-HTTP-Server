package mite.bodies

import mite.http.HTTP.Request
import mite.util.HTML

class FormBodyHandler : HTML {
    fun of() = FunctionBodyHandler { request: Request ->
        html(body(pre("""
            request = $request
            method  = ${request.method}
            filename= ${request.filename}
            """.trimIndent())))
    }
}