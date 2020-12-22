package mite.bodies

import mite.core.HTTPRequest

/**
 * Simple handler mostly for demonstration and debugging.
 */
object EchoRequestHandler {
    fun of(): FunctionBodyHandler {
        return FunctionBodyHandler.of { request: HTTPRequest ->
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