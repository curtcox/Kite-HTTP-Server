package mite.core

import java.io.*
import mite.http.HTTP.*

object HTTPResponseWriter {

    @Throws(IOException::class)
    fun write(version:Version, response: Response,  out: OutputStream) {
        val writer: Writer = OutputStreamWriter(out)
        writer.use {
            version.writeHeaders(response.headers,writer)
            val body = response.body
            if (body.contentType.binary)
                out.write(body.bytes)
            else
                writer.write(body.page)
        }
    }

}