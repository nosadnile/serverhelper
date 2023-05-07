package net.nosadnile.gradle.serverhelper.util

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.nosadnile.gradle.serverhelper.schema.PaperMeta
import java.net.URL

class VelocityVersionHelper {
    companion object Helper {
        private val json = Json { ignoreUnknownKeys = true }

        @JvmStatic
        fun getServerJar(version: String): String {
            val versionsUrl = "https://papermc.io/api/v2/projects/velocity/versions"
            val versionsJson = URL("$versionsUrl/$version").readText()
            val build = json.decodeFromString<PaperMeta.Builds>(versionsJson).builds.maxOrNull()

            return "$versionsUrl/$version/builds/$build/downloads/velocity-$version-$build.jar"
        }
    }
}