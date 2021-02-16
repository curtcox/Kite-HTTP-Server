package mite.html

data class Table(val head:String,val body:String) : HTML {

    override fun toHtml() = table(
"""
${thead(head)}
${tbody(body)}""")

}