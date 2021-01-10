package mite.core

import java.io.*

/**
 * Reads a single HTTP request.
 */
internal object RequestReader {

//    @Throws(IOException::class)
//    fun readRequest(input: InputStream): Array<String> {
//        val reader = BufferedReader(input.reader())
//        reader.use {
//            return reader.readLines().toTypedArray()
//        }
//    }

    @Throws(IOException::class)
    fun readRequest(input: InputStream): Array<String> {
        val lines = ArrayList<String>()
        val line = StringBuilder()
        val max_bytes_in_request = input.available()
        for (i in 0 until max_bytes_in_request) {
            val c = input.read()
            if (c==-1) break
            if (c == '\r'.toInt() || c == '\n'.toInt()) {
                lines.add(line.toString())
            }
            line.append(c.toChar())
        }
        return lines.toTypedArray()
    }

}