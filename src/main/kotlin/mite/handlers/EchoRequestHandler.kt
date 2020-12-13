package mite.handlers

import mite.HTTPRequest

/**
 * Simple handler mostly for demonstration and debugging.
 */
object EchoRequestHandler {
    fun of(): FunctionRequestHandler {
        return FunctionRequestHandler.of { request:HTTPRequest ->
            """
<html>
  <body>
    <pre>
       request = $request
       method  = ${request.method}
       filename= ${request.filename}
    </pre>
  </body>
</html>
            """.trimIndent()
        }
    }
}