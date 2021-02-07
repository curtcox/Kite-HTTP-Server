package mite.bodies

import mite.ast.Node
import mite.http.HTTP.*
import mite.renderers.HTML
import mite.util.*

/**
 * Displays preferences.
 */
object PreferencesRequestHandler : HTML {

    fun of() = FunctionBodyHandler("/prefs") { request: Request ->
        Node.leaf(PersistentStorage::class,PersistentStorage)
    }

}