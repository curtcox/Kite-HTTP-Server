package mite.handlers

import mite.HTTPRequest
import mite.HTTPRequestHandler
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*
import java.util.function.Function
import java.util.stream.Stream

/**
 * Handlers that
 */
object ProcessRequestHandler {
    @JvmOverloads
    fun of(
        f: Function<HTTPRequest, List<String>> = Function { (_, _, filename) ->
            Arrays.asList(
                *filename.substring(1).split("\\+").toTypedArray()
            )
        }
    ): HTTPRequestHandler {
        return FunctionRequestHandler.of { httpRequest -> run(f.apply(httpRequest)) }
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