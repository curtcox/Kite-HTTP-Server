package mite.bodies

import mite.ast.*
import mite.ihttp.InternalHttp.*
import mite.util.*

/**
 * Displays preferences.
 */
object PreferencesRequestHandler {

    fun of() = FunctionBodyHandler("/prefs", { request: InternalRequest ->
        SimpleNode.leaf(PersistentStorage::class,PersistentStorage)
    })

}