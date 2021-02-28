package mite

import mite.http.HTTP.*
import mite.ihttp.InternalHttp

object TestObjects {

    val request = internalRequestForFilename("")

    fun internalRequestForFilename(filename:String,version:Version = Version.Unknown) =
        InternalHttp.InternalRequest(requestForFilename(filename,version))

    fun requestForFilename(filename:String,version:Version = Version.Unknown) =
        Request(Request.Raw(arrayOf()),
            Request.Method.UNKNOWN,
            "",
            filename,
            ContentType.FORM_URLENCODED,
            version)

}