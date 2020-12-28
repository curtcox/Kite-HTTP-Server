package mite.util

import org.junit.Test
import java.io.ByteArrayOutputStream
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
        val map = storage.get(key)
        assertTrue(map.contains(value))
    }

}