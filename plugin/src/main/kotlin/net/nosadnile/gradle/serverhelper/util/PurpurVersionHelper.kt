package net.nosadnile.gradle.serverhelper.util

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.nosadnile.gradle.serverhelper.schema.PurpurMeta
import java.net.URL

class PurpurVersionHelper {
    companion object Helper {
        private val json = Json { ignoreUnknownKeys = true }

        @JvmStatic
        fun getServerJar(version: String): String {
            val versionsUrl = "https://api.purpurmc.org/v2/purpur"
            val versionsJson = URL("$versionsUrl/$version").readText()
            val build = json.decodeFromString<PurpurMeta.PurpurVersion>(versionsJson).builds.latest

            return "$versionsUrl/$version/$build/download"
        }
    }
}