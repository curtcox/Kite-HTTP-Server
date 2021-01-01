package mite.handlers

import mite.bodies.AbstractBodyHandler
import mite.core.*
import java.io.File

object FaviconHandler : AbstractBodyHandler("/favicon.ico") {

    override fun handle(request: HTTPRequest) =
        HTTPResponse.bytes(File("favicon.ico").readBytes(),ContentType.ICON)
}