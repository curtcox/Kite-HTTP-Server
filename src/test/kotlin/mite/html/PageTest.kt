package mite.html

import mite.core.ExchangeTracker
import mite.core.Log
import org.junit.Test
import kotlin.test.*

class PageTest {

    @Test
    fun `can create`() {
        ExchangeTracker.nextInfo()
        val page = Page.of("","","",HTML.Tags.string(""))
        assertTrue(page is Page)
    }

    @Test
    fun `has expected field values`() {
        ExchangeTracker.nextInfo()
        val css = "css"
        val script = "script"
        val title = "title"
        val body = "body"
        val page = Page.of(css,script,title,HTML.Tags.string(body))
        assertEquals(css,page.css)
        assertEquals(script,page.script)
        assertEquals(title,page.title)
        assertEquals(body,page.bodyText.toHtml())
    }

    @Test
    fun `has empty default field values`() {
        ExchangeTracker.nextInfo()
        val css = ""
        val script = ""
        val title = "title"
        val body = "body"
        val page = Page.of(title=title,bodyText = HTML.Tags.string(body))
        assertEquals(css,page.css)
        assertEquals(script,page.script)
        assertEquals(title,page.title)
        assertEquals(body,page.bodyText.toHtml())
    }

    @Test
    fun `creation is logged`() {
        ExchangeTracker.nextInfo()
        val page = Page.of("","","",HTML.Tags.string(""))
        assertTrue(Log.entries.any{it.record==page})
    }

    @Test
    fun `creation log contains stack reflecting creation`() {
        ExchangeTracker.nextInfo()
        val page = Page.of("","","",HTML.Tags.string(""))
        val entry = Log.entries.first{it.record==page}
        assertEquals(Page::class,entry.logger)
        assertEquals(page,entry.record)
        val stack = entry.stack.stackTrace
        assertTrue(stack.any{it.fileName=="PageTest.kt" && it.lineNumber==55 })
    }

    @Test
    fun `page throwable contains stack reflecting creation`() {
        ExchangeTracker.nextInfo()
        val page = Page.of("","","",HTML.Tags.string(""))
        val stack = page.created.stackTrace
        assertTrue(stack.any{it.fileName=="PageTest.kt" && it.lineNumber==66 })
    }

}