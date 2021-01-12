package mite.bodies

import mite.core.HTTP.*
import mite.util.*

/**
 * Displays preferences.
 */
object PreferencesRequestHandler : HTML {

    private fun htmlPrefs() = PersistentStorage.toString()
        .replace("<", "&lt;")
        .replace(">", "&gt;")

    fun of() = FunctionBodyHandler("/prefs") { request: Request ->
        html(body(pre(htmlPrefs())))
    }

}