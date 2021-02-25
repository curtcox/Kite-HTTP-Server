package mite.http

import mite.core.Log
import java.lang.Exception
import java.util.*
import mite.http.HTTP.*
import mite.http.HTTP.Request.*

object RequestParser {

    private fun host(raw: Raw) = raw.lines
        .filter { it.startsWith("Host:") }
        .map { x -> x.split(" ")[1] }
        .firstOrNull() ?: "UNKNOWN"

    fun parse(raw: Raw): Request {
        return try {
            val first = raw.lines[0]
            val tokenizer = StringTokenizer(first)
            val method = when(tokenizer.nextToken()) {
                "GET"  -> Method.GET
                "POST" -> Method.POST
                else   -> Method.UNKNOWN
            }
            val filename = tokenizer.nextToken()
            Request(raw, method, host(raw), filename, ContentType.FORM_URLENCODED, Version.fromRequest(first))
        } catch (e: Exception) {
            Log.log(Request::class,e)
            Request(raw, Method.UNKNOWN, "", "", ContentType.FORM_URLENCODED, Version.Unknown)
        }
    }

}