package mite.html

/**
 * An HTML table.
 */
data class Table(val head:Row,val body:Body) : HTML {

    data class Row(val cells:List<String>,val type:String) : HTML {
        override fun toHtml(): String {
            val out = StringBuilder()
            for (e in cells) {
                out.append("<$type>$e</$type>")
            }
            return "<TR>$out</TR>"
        }
    }

    data class Body(val rows:List<Row>) : HTML {
        override fun toHtml(): String {
            val out = StringBuilder()
            for (r in rows) {
                out.append(r.toHtml())
            }
            return "<tbody>$out</tbody>"
        }
    }

    val css = """<link rel="stylesheet" type="text/css" href="/datatables.min.css"/>"""

    val javascript = """
        <script type="text/javascript" src="/jquery-3.5.1.min.js"></script>
        <script type="text/javascript" src="/datatables.min.js"></script>
        """.trimIndent()

    // See https://datatables.net/reference/option/paging
    val documentReady =
"""${'$'}(document).ready(function() {
   ${'$'}('#table_id').DataTable({
      "paging": false,
      dom: 'Bfrtip',
      buttons: [ 'colvis', 'copy', 'csv', 'excel', 'pdf', 'print'],
      rowReorder: true,
      colReorder: true,
      fixedHeader: true
   });
} );
""".trimIndent()

    override fun toHtml() =
        css +
        javascript +
        script(documentReady) +

        table("""
                ${thead(head.toHtml())}
                ${body.toHtml()}""".trimIndent())

    private fun table(text:String) = tag(text,"""<table id="table_id" class="display responsive wrap" style="width:100%">""","</table>")
    private fun thead(text:String) = tag(text,"<thead>","</thead>")

}