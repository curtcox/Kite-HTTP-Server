package mite.util

import org.junit.Test
import kotlin.test.*

class PersistentStorageTest {

    val storage = PersistentStorage

    @Test
    fun `can export`() {
        PersistentStorage.main(arrayOf("o"))
    }

    @Test
    fun `export contains expected text`() {
        val exported = storage.toString()
        for (text in arrayOf("preferences","root","map","node","user","mite","util")) {
            assertTrue(exported.contains(text))
        }
    }

    @Test
    fun `export contains new key and value`() {
        val key = "foo"
        val value = "bar"
        storage.put(key,value)
        val exported = storage.toString()
        assertTrue(exported.contains(key))
        assertTrue(exported.contains(value))
    }

    @Test
    fun `can get new key and value`() {
        val key = "foo"
        val value = "bar"
        storage.put(key,value)
        val list = storage.get(key)
        assertTrue(list.contains(value),list.toString())
    }

    @Test
    fun `adding a value doesn't change if key already contains it`() {
        val key = "foo"
        val value = "bar"
        storage.put(key,value)
        val before = storage.get(key)
        storage.put(key,value)
        val after = storage.get(key)
        assertEquals(before,after)
    }

}