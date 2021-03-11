package mite.html

object ThrowableRenderer : HTML.Renderer {

    override fun renders(a: Any?) = a is Throwable

    override fun render(a: Any?): HTML = table(a as Throwable)

    private fun table(t:Throwable) = Table(head(),body(t),caption(t))

    private fun caption(t:Throwable) = "$t"

    private fun head() = Table.Row(listOf("Class Name", "File Name", "line number"), "TH")

    private fun body(t:Throwable) = Table.Body(t.stackTrace.map { e -> row(e) })

    private fun row(element: StackTraceElement) = Table.Row(cells(element), "TD")

    private fun cells(element: StackTraceElement) =
        if (element.fileName==null) listOf(element.className, "null", "null")
        else                        listOf(element.className, linkToFileName(element), linkToLineNumber(element))

    private fun linkToFileName(element: StackTraceElement) = linkTo(element.fileName,urlFor(element))

    private fun linkToLineNumber(element: StackTraceElement) = linkTo("${element.lineNumber}",urlFor(element))

    private fun linkTo(label:String,target:String) = """<a href="$target">$label</a>"""

    private fun urlFor(element: StackTraceElement) = "/source/${path(element)}#${element.lineNumber}"

    private fun path(element: StackTraceElement) = element.className.replace(".","/") + ".kt"

}