package mite.util

import java.io.*
import java.util.prefs.Preferences
import kotlin.collections.*

object PersistentStorage {

    val prefs = Preferences.userNodeForPackage(PersistentStorage::class.java)

    private val N = "\n"
    private fun importPrefs() = Preferences.importPreferences(System.`in`)
    private fun exportPrefs(out:OutputStream = System.out) = prefs.exportNode(out)
    fun get(key:String):Set<String> = setOf(prefs.get(key,""))
    fun put(key:String,value:String) {
        val set = setOf(prefs.get(key,""))
        set.add(value)
        prefs.put(key,stringOf(set))
    }

    private fun setOf(string:String) = HashSet(string.split(N))
    private fun stringOf(set:Set<String>) = set.joinToString(separator = N)

    @JvmStatic
    fun main(args: Array<String>) {
        when (args.size) {
            1 -> when (args[0]) {
                    "i" -> importPrefs()
                    "o" -> exportPrefs()
                }
            2 -> when (args[0]) {
                    "get" -> get(args[1])
                }
            3 -> when (args[0]) {
                    "put" -> put(args[1],args[2])
                }
            else -> printHelp()
        }

    }

    override fun toString() : String {
        val out = ByteArrayOutputStream()
        exportPrefs(out)
        return out.toString()
    }

    private fun printHelp() {
print("""
   i : read in preferences
   o : write out preferences
 get : get named preference values
 put : put named preference value
""")
    }
}