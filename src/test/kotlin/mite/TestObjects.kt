package mite

import mite.http.HTTP.*

object TestObjects {

    val request = requestForFilename("")

    fun requestForFilename(filename:String,version:Version = Version.Unknown) =
        Request(Request.Raw(arrayOf()),
            Request.Method.UNKNOWN,
            "",
            filename,
            ContentType.FORM_URLENCODED,
            version)

}