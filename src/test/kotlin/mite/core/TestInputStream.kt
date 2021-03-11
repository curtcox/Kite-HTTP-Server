package mite.core

import java.io.*

class TestInputStream(val inner:InputStream,val fail:Boolean,val max:Int) : InputStream() {

    var offset = 0;

    constructor(lines:Int,fail:Boolean,max:Int) : this(input(lines),fail,max)

    override fun read(): Int {
        if (offset>max && fail) {
            throw IOException()
        }
        offset++
        return inner.read()
    }
    
    companion object {
        fun input(n:Int) : InputStream {
            val out = ByteArrayOutputStream()
            val writer = BufferedWriter(OutputStreamWriter(out))
            repeat(n) {
                writer.write("line")
                writer.newLine()
            }
            writer.close()
            return ByteArrayInputStream(out.toByteArray())
        }
    }
}