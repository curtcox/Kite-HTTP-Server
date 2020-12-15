package mite.handlers

import mite.*
import java.io.*
import java.util.*
import java.util.stream.Stream

/**
 * Handlers that execute processes and return the results.
 */
object ProcessRequestHandler {

    @JvmOverloads
    fun of(
        f: (HTTPRequest) -> List<String> = { request -> command(request) }
    ): HTTPRequestHandler {
        return FunctionRequestHandler.of { httpRequest -> run(f.invoke(httpRequest)) }
    }

    private fun command(request: HTTPRequest): List<String> {
        val filename = request.filename
        val strings = filename.substring(1).split("\\+").toTypedArray()
        return Arrays.asList(*strings)
    }

    private fun run(params: List<String>): String {
        return try {
            runCommandForOutput(params)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private fun outputOf(process: Process): Stream<String> {
        return BufferedReader(InputStreamReader(process.inputStream)).lines()
    }

    @Throws(IOException::class, InterruptedException::class)
    private fun runCommandForOutput(params: List<String>): String {
        val builder = ProcessBuilder(params)
        val process = builder.start()
        val stringJoiner = StringJoiner(System.getProperty("line.separator"))
        outputOf(process).iterator().forEachRemaining { newElement: String? ->
            stringJoiner.add(
                newElement
            )
        }
        process.waitFor()
        process.destroy()
        return stringJoiner.toString()
    }
}