package mite.bodies

import mite.core.*
import mite.util.*

/**
 * Displays preferences.
 */
object PreferencesRequestHandler : HTML {

    private fun htmlPrefs() = PersistentStorage.toString()
        .replace("<", "&lt;")
        .replace(">", "&gt;")

    fun of(): FunctionBodyHandler {
        return FunctionBodyHandler.of("/prefs") { request: HTTPRequest ->
            html(body(pre(htmlPrefs())))
        }
    }

}