package mite.bodies

import mite.core.*
import mite.util.PersistentStorage

/**
 * Displays preferences.
 */
object PreferencesRequestHandler {

    fun of(): FunctionBodyHandler {
        return FunctionBodyHandler.of("/prefs", { request: HTTPRequest ->
            val prefs = PersistentStorage.toString()
                .replace("<","&lt;")
                .replace(">","&gt;")
            """
<html>
  <body>
    <pre>
       $prefs
    </pre>
  </body>
</html>
            """.trimIndent()
        })
    }


}