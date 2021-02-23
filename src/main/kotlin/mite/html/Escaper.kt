package mite.html

/**
 * Escapes HTML in the given string and truncates the length to around an optional length.
 * See https://stackoverflow.com/questions/6502759/how-to-strip-or-escape-html-tags-in-android
 */
object Escaper {

    fun escape(text: String,max:Int = 1000) : String {
        val out = StringBuilder()
        var i = 0
        val end = text.length
        while (i < end && out.length < max) {
            val c = text[i]
            if (c == '<') {
                out.append("&lt;")
            } else if (c == '>') {
                out.append("&gt;")
            } else if (c == '&') {
                out.append("&amp;")
            } else if (c.toInt() >= 0xD800 && c.toInt() <= 0xDFFF) {
                if (c.toInt() < 0xDC00 && i + 1 < end) {
                    val d = text[i + 1]
                    if (d.toInt() >= 0xDC00 && d.toInt() <= 0xDFFF) {
                        i++
                        val codepoint = 0x010000 or (c.toInt() - 0xD800 shl 10) or d.toInt() - 0xDC00
                        out.append("&#").append(codepoint).append(";")
                    }
                }
            } else if (c.toInt() > 0x7E || c < ' ') {
                out.append("&#").append(c.toInt()).append(";")
            } else if (c == ' ') {
                while (i + 1 < end && text[i + 1] == ' ') {
                    out.append("&nbsp;")
                    i++
                }
                out.append(' ')
            } else {
                out.append(c)
            }
            i++
        }
        return out.toString()
    }

}