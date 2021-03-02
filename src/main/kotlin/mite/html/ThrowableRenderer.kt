package mite.html

object ThrowableRenderer : HTML.Renderer {

    override fun renders(a: Any?) = a is Throwable

    override fun render(a: Any?): HTML = table(a as Throwable)

    private fun table(t:Throwable) = Table(head(),body(t))

    private fun head() = Table.Row(listOf("Class Name", "File Name", "line number"), "TH")

    private fun body(t:Throwable) = Table.Body(t.stackTrace.map { e -> row(e) })

    private fun row(element: StackTraceElement) = Table.Row(
        listOf(
            element.className,
            element.fileName,
            "${element.lineNumber}"
        ), "TD"
    )

}