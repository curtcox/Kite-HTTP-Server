package mite.bodies

import mite.ast.*
import mite.http.HTTP.*
import mite.html.HTML
import mite.util.*

/**
 * Displays preferences.
 */
object PreferencesRequestHandler {

    fun of() = FunctionBodyHandler("/prefs", { request: Request ->
        SimpleNode.leaf(PersistentStorage::class,PersistentStorage)
    })

}