package net.nosadnile.gradle.serverhelper.api

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

import net.nosadnile.gradle.serverhelper.schema.PistonMeta

import java.net.URL

class PistonAPI {
    companion object Helper {
        private val json = Json { ignoreUnknownKeys = true }

        @JvmStatic
        fun getLatestMinecraftVersion(snapshot: Boolean = false): String {
            val versionManifestText = URL("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json").readText()
            val versionManifest = json.decodeFromString<PistonMeta.VersionManifestV2>(versionManifestText)

            return if (snapshot) versionManifest.latest.snapshot else versionManifest.latest.release
        }

        @JvmStatic
        fun getMinecraftServerJar(version: String): String? {
            val versionManifestText = URL("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json").readText()
            val versionManifest = json.decodeFromString<PistonMeta.VersionManifestV2>(versionManifestText)
            val correct = versionManifest.versions.find { element -> element.id == version }
            val manifestUrl = correct?.url ?: return null
            val manifestText = URL(manifestUrl).readText()
            val manifest = json.decodeFromString<PistonMeta.VersionManifest>(manifestText)

            return manifest.downloads.server.url
        }
    }
}
