package mite.html

object ThrowableRenderer : HTML.Renderer {

    override fun renders(a: Any?) = a is Throwable

    override fun render(a: Any?): HTML = table(a as Throwable)

    private fun table(t:Throwable) = Table(head(),body(t),"Throwable")

    private fun head() = Table.Row(listOf("Class Name", "File Name", "line number"), "TH")

    private fun body(t:Throwable) = Table.Body(t.stackTrace.map { e -> row(e) })

    private fun row(element: StackTraceElement) = Table.Row(
        listOf(
            element.className,
            linkToFileName(element),
            linkToLineNumber(element)
        ), "TD"
    )

    private fun linkToFileName(element: StackTraceElement) = linkTo(element.fileName,linkTo(element))

    private fun linkToLineNumber(element: StackTraceElement) = linkTo("${element.lineNumber}",linkTo(element))

    private fun linkTo(label:String,target:String) = """<a href="$target">$label</a>"""

    private fun linkTo(element: StackTraceElement) = "/source/${element.className}#${element.lineNumber}"

}