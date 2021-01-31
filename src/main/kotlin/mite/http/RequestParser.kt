package mite.http

import java.lang.Exception
import java.util.*

object RequestParser {

    private fun host(raw: HTTP.Request.Raw) = raw.lines
        .filter { it.startsWith("Host:") }
        .map { x -> x.split(" ")[1] }
        .first()

    fun parse(raw: HTTP.Request.Raw): HTTP.Request {
        return try {
            val first = raw.lines[0]
            val tokenizer = StringTokenizer(first)
            val method = when(tokenizer.nextToken()) {
                "GET"  -> HTTP.Request.Method.GET
                "POST" -> HTTP.Request.Method.POST
                else   -> HTTP.Request.Method.UNKNOWN
            }
            val filename = tokenizer.nextToken()
            HTTP.Request(raw,
                method,
                host(raw),
                filename,
                HTTP.ContentType.FORM_URLENCODED,
                HTTP.Version.fromRequest(first))
        } catch (e: Exception) {
            HTTP.Request(raw,
                HTTP.Request.Method.UNKNOWN,
                "",
                "",
                HTTP.ContentType.FORM_URLENCODED,
                HTTP.Version.Unknown)
        }
    }

}