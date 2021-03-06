package mite.handlers

import mite.bodies.AbstractBodyHandler
import mite.http.HTTP.*
import java.io.File

object FaviconHandler : AbstractBodyHandler("/favicon.ico") {

    override fun handle(request: Request) =
        InternalResponse.OK(File("resources/favicon.ico").readBytes(),ContentType.ICON)
}