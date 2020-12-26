package mite.util

import java.util.prefs.Preferences
import java.util.*

object PersistentStorage {

    val prefs = Preferences.userNodeForPackage(PersistentStorage::class.java)

    private fun importPrefs() = Preferences.importPreferences(System.`in`)
    private fun exportPrefs() = prefs.exportNode(System.out)
    private fun get(key:String) = prefs.get(key,"")
    private fun put(key:String,value:String) {
        val map = mapOf(prefs.get(key,""))
        map.put(key,value)
        prefs.put(key,stringOf(map))
    }

    private fun stringOf(m:HashMap<String,String>) = m.toString()

    private fun mapOf(s:String) = s.split("\n").associateTo(HashMap()) {
        val (left, right) = it.split("=")
        left to right
    }

    @JvmStatic
    fun main(args: Array<String>) {
        when (args.size) {
            1 -> {
                when (args[0]) {
                    "i" -> importPrefs()
                    "o" -> exportPrefs()
                }
            }
            2 -> {
                when (args[0]) {
                    "get" -> get(args[1])
                }
            }
            3 -> {
                when (args[0]) {
                    "put" -> put(args[1],args[2])
                }
            }
            else -> printHelp()
        }

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