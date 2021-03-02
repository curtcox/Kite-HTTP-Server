package mite.html

import mite.html.HTML.Tags.script
import mite.html.HTML.Tags.tag

/**
 * An HTML table.
 */
data class Table(val head:Row,val body:Body,val caption:String) : HTML {

    val tableId = "${caption}_table"

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
   ${'$'}('#$tableId').DataTable({
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
                ${tag(caption,"<cationn>","</caption>")}
                ${thead(head.toHtml())}
                ${body.toHtml()}""".trimIndent())

    private fun table(body:String) = tag(body,"""<table id="$tableId" class="display responsive wrap" style="width:100%">""","</table>")
    private fun thead(text:String) = tag(text,"<thead>","</thead>")

}