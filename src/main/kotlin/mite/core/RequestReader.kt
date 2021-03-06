package mite.core

import java.io.*
import mite.http.HTTP.Request.Raw

/**
 * Reads a single HTTP request.
 */
internal object RequestReader {

    @Throws(IOException::class)
    fun readRequest(input: InputStream): Raw {
        val reader = BufferedReader(input.reader())
        val lines = ArrayList<String>()
        while (reader.ready() || lines.isEmpty()) { // <-- This line is vital. It needs a test
            val line = reader.readLine()
            if (line!=null) {
                lines.add(line)
            }
        }
        return Raw(lines.toTypedArray())
    }

}