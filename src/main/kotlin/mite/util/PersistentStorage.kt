package mite.util

import java.io.*
import java.util.prefs.Preferences
import java.util.*
import kotlin.collections.HashMap

object PersistentStorage {

    val prefs = Preferences.userNodeForPackage(PersistentStorage::class.java)

    private fun importPrefs() = Preferences.importPreferences(System.`in`)
    private fun exportPrefs(out:OutputStream = System.out) = prefs.exportNode(out)
    fun get(key:String) = prefs.get(key,"")
    fun put(key:String,value:String) {
        val map = mapOf(prefs.get(key,""))
        map.put(key,value)
        prefs.put(key,stringOf(map))
    }

    private fun stringOf(m:HashMap<String,String>) = m.toString()

    private fun mapOf(s:String): HashMap<String, String> {
        val map = HashMap<String,String>()
        for (line in s.split("\n")) {
            val parts = line.split("=")
            if (parts.size==2)
                map.put(parts[0],parts[1])
        }
        return map
    }

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