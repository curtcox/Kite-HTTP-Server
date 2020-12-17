package mite.handlers

import mite.*
import org.junit.Test
import kotlin.test.*

class CachedRequestHandlerTest {

    fun page() = object: AbstractRequestHandler() {
        var count = 0
        override fun handle(request: HTTPRequest) = HTTPResponse.of((++count).toString(), StatusCode.OK)
    }

    fun empty() = object: AbstractRequestHandler() {
        override fun handle(request: HTTPRequest) = null
    }

    fun boom() = object: AbstractRequestHandler() {
        override fun handle(request: HTTPRequest): HTTPResponse? {
            throw IllegalAccessError()
        }
    }

    fun request(string:String) = HTTPRequest.parse(string)

    @Test
    fun handles_is_true_when_cached_handler_returns_string() {
        val cached = cached(page())
        assertTrue(cached.handles(request("foo")))
    }

    fun cached(handler: HTTPRequestHandler) = CachedRequestHandler.of(handler)

    @Test
    fun handle_returns_string_from_cached_handler() {
        val cached = cached(page())
        val response = cached.handle(request("foo"))!!
        assertEquals("1",response.page)
    }

    @Test
    fun handles_is_false_when_cached_handler_returns_null() {
        val cached = cached(empty())
        assertFalse(cached.handles(request("foo")))
    }

    @Test
    fun handles_is_false_when_cached_handler_throws_exception() {
        val cached = boom()
        assertFalse(cached.handles(request("foo")))
    }

    @Test
    fun handles_followed_by_handle_for_same_request_only_invokes_handle_once() {
        val cached = cached(page())
        val request = request("foo")
        assertTrue(cached.handles(request))
        val response = cached.handle(request)!!
        assertEquals("1",response.page)
    }

    @Test
    fun handles_followed_by_handle_for_different_request_invokes_handle_twice() {
        val cached = cached(page())
        assertTrue(cached.handles(request("first")))
        val response = cached.handle(request("second"))!!
        assertEquals("2",response.page)
    }

}