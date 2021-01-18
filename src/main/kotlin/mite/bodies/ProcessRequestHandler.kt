package mite.bodies

import mite.ast.Node
import mite.http.HTTP.*
import java.io.*
import java.util.*
import java.util.stream.Stream
import kotlin.streams.toList

/**
 * Handlers that execute processes and return the results.
 */
object ProcessRequestHandler {

    @JvmOverloads
    fun of(
        f: (Request) -> List<String> = { request -> command(request) }
    ): BodyHandler {
        return FunctionBodyHandler { httpRequest -> Node.list(Process::class,run(f.invoke(httpRequest))) }
    }

    private fun command(request: Request): List<String> {
        val filename = request.filename
        val strings = filename.substring(1).split("\\+").toTypedArray()
        return Arrays.asList(*strings)
    }

    private fun run(params: List<String>): List<String> {
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
    private fun runCommandForOutput(params: List<String>): List<String> {
        val builder = ProcessBuilder(params)
        val process = builder.start()
        val stringJoiner = StringJoiner(System.getProperty("line.separator"))
        val out = outputOf(process).toList()
        process.waitFor()
        process.destroy()
        return out
    }
}