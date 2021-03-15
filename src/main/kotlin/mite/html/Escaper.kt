package mite.html

/**
 * Escapes HTML in the given string and truncates the length to around an optional length.
 * See https://stackoverflow.com/questions/6502759/how-to-strip-or-escape-html-tags-in-android
 */
object Escaper {

    private class F(val text: String,val max:Int = 1000) {
        val out = StringBuilder()
        val end = text.length
        var i = 0

        fun exec() : String {
            while (i < end && out.length < max) {
                process(text[i])
                i++
            }
            return out.toString()
        }

        private fun process(c:Char) {
                   if (c == '<') { out.append("&lt;")
            } else if (c == '>') { out.append("&gt;")
            } else if (c == '&') { out.append("&amp;")
            } else if (between(c,0xD800,0xDFFF)) { processReserved(c)
            } else if (c.toInt() > 0x7E || c < ' ') { out.append("&#").append(c.toInt()).append(";")
            } else if (c == ' ') { processSpaces()
            } else { out.append(c) }
        }

        private fun processReserved(c:Char) {
            if (c.toInt() < 0xDC00 && i + 1 < end) {
                val d = text[i + 1]
                if (between(d,0xDC00,0xDFFF)) {
                    i++
                    val codepoint = 0x010000 or (c.toInt() - 0xD800 shl 10) or d.toInt() - 0xDC00
                    out.append("&#").append(codepoint).append(";")
                }
            }
        }

        private fun processSpaces() {
            while (i + 1 < end && text[i + 1] == ' ') {
                out.append("&nbsp;")
                i++
            }
            out.append(' ')
        }

        private fun between(c:Char,lo:Int,hi:Int) = c.toInt() >= lo && c.toInt() <= hi

    }

    fun escape(text: String,max:Int = 1000) = F(text,max).exec()

}