package mite.core

import java.io.*
import mite.http.HTTP.*

object HTTPResponseWriter {

    @Throws(IOException::class)
    fun write(version:Version, response: Response, headers: Array<Header>,  out: OutputStream) {
        val writer: Writer = OutputStreamWriter(out)
        writer.use {
            version.writeHeaders(headers,writer)
            if (response.contentType.binary)
                out.write(response.bytes)
            else
                writer.write(response.page)
        }
    }

}