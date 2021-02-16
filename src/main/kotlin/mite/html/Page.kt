package mite.html

data class Page(val bodyText:HTML) : HTML {

    override fun toHtml() = html(body(bodyText.toHtml()))

}