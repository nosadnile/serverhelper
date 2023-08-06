package net.nosadnile.gradle.serverhelper.api

class ConfigUtil {
    companion object Helper {
        @JvmStatic
        fun toMap(servers: List<Pair<String, Int>>): MutableMap<String, Int> {
            val map = HashMap<String, Int>()

            for (server in servers) {
                map[server.first] = server.second
            }

            return map
        }
    }
}