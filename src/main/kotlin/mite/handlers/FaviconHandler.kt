package mite.handlers

import mite.bodies.AbstractBodyHandler
import mite.core.HTTP.*
import java.io.File

object FaviconHandler : AbstractBodyHandler("/favicon.ico") {

    override fun handle(request: Request) =
        Response.bytes(File("favicon.ico").readBytes(),ContentType.ICON)
}