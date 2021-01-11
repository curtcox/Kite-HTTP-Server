package mite.core

import java.io.*

/**
 * Reads a single HTTP request.
 */
internal object RequestReader {

    @Throws(IOException::class)
    fun readRequest(input: InputStream): Array<String> {
        val reader = BufferedReader(input.reader())
        val lines = ArrayList<String>()
        while (reader.ready() || lines.isEmpty()) { // <-- This line is vital. It needs a test
            val line = reader.readLine()
            lines.add(line)
        }
        return lines.toTypedArray()
    }

}