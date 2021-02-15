package mite.renderers

interface HTML {

    fun css() = """<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.23/datatables.min.css"/>"""
    fun javascript() = """<script type="text/javascript" src="DataTables/datatables.min.js"></script>"""
    fun documentReady() =
"""${'$'}(document).ready(function() {
   ${'$'}('#table_id').DataTable();
} );
"""

    fun body(text:String) = tag(text,"<BODY>","</BODY>")
    fun head(text:String) = tag(text,"<HEAD>","</HEAD>")
    fun title(text:String) = tag(text,"<TITLE>","</TITLE>")
    fun h1(text:String) = tag(text,"<H1>","</H1>")
    fun pre(text:String) = tag(text,"<PRE>","</PRE>")
    fun html(text:String) = tag(text,"<HTML>","</HTML>")
    fun table(text:String) = tag(text,"""<table id="table_id" class="display">""","</table>")
    fun thead(text:String) = tag(text,"<thead>","</thead>")
    fun tbody(text:String) = tag(text,"<tbody>","</tbody>")

    fun tag(text:String,open:String,close:String) =
"""
$open
$text
$close
""".trim()

}