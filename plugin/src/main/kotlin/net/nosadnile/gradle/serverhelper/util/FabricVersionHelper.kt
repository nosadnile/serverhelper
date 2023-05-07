package net.nosadnile.gradle.serverhelper.util

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.nosadnile.gradle.serverhelper.schema.FabricVersions
import java.net.URL

class FabricVersionHelper {
    companion object Helper {
        private val json = Json { ignoreUnknownKeys = true }

        @JvmStatic
        fun getServerJar(minecraftVersion: String): String {
            val loaderVersionsUrl = "https://meta.fabricmc.net/v2/versions/loader"
            val loaderVersionsJson = URL(loaderVersionsUrl).readText()

            val latestLoaderVersion =
                json.decodeFromString<List<FabricVersions.FabricLoaderVersion>>(loaderVersionsJson).filter { it.stable }
                    .sortedWith(
                        compareBy(
                            { it.version.split(it.separator)[0] },
                            { it.version.split(it.separator)[1] },
                            { it.version.split(it.separator)[2] }
                        )
                    ).asReversed()[0].version

            return getServerJar(minecraftVersion, latestLoaderVersion)
        }

        @JvmStatic
        fun getServerJar(minecraftVersion: String, loaderVersion: String): String {
            val loaderVersionsUrl = "https://meta.fabricmc.net/v2/versions/loader"
            val installerVersionsUrl = "https://meta.fabricmc.net/v2/versions/installer"
            val installerVersionsJson = URL(installerVersionsUrl).readText()

            val latestInstallerVersion =
                json.decodeFromString<List<FabricVersions.FabricInstallerVersion>>(installerVersionsJson)
                    .filter { it.stable }
                    .sortedWith(
                        compareBy(
                            { it.version.split(".")[0] },
                            { it.version.split(".")[1] },
                            { it.version.split(".")[2] }
                        )
                    ).asReversed()[0].version

            return "$loaderVersionsUrl/$minecraftVersion/$loaderVersion/$latestInstallerVersion/server/jar"
        }
    }
}