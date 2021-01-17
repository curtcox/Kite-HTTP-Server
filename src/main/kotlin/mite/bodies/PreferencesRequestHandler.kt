package mite.bodies

import mite.ast.Node
import mite.http.HTTP.*
import mite.util.*

/**
 * Displays preferences.
 */
object PreferencesRequestHandler : HTML {

    fun of() = FunctionBodyHandler("/prefs") { request: Request ->
        Node.leaf(PersistentStorage,PersistentStorage)
    }

}