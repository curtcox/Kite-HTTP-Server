package mite.html

data class Table(val head:String,val body:String) : HTML {

    val css = """<link rel="stylesheet" type="text/css" href="/datatables.min.css"/>"""
    val javascript = """
        <script type="text/javascript" src="/jquery-3.5.1.min.js"></script>
        <script type="text/javascript" src="/datatables.min.js"></script>
        """.trimIndent()
    val documentReady =
        """${'$'}(document).ready(function() {
   ${'$'}('#table_id').DataTable();
} );
"""


    override fun toHtml() =
    css +
    javascript +
    script(documentReady) +

    table("""
${thead(head)}
${tbody(body)}""")

    fun table(text:String) = tag(text,"""<table id="table_id" class="display">""","</table>")
    fun thead(text:String) = tag(text,"<thead>","</thead>")
    fun tbody(text:String) = tag(text,"<tbody>","</tbody>")

}