package mite.core

import java.io.*

object HTTPResponseWriter {

    @Throws(IOException::class)
    fun write(version:HTTPVersion, response: HTTPResponse, headers: Array<HTTPHeader>,  out: OutputStream) {
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