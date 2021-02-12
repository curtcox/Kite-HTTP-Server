package mite.bodies

import mite.ast.*
import mite.http.HTTP.*
import mite.renderers.HTML
import mite.util.*

/**
 * Displays preferences.
 */
object PreferencesRequestHandler : HTML {

    fun of() = FunctionBodyHandler("/prefs") { request: Request ->
        SimpleNode.leaf(PersistentStorage::class,PersistentStorage)
    }

}