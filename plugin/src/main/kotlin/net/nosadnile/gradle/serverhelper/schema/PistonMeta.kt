package net.nosadnile.gradle.serverhelper.schema

import kotlinx.serialization.Serializable

class PistonMeta {
    @Serializable
    data class LatestVersions(
        val release: String,
        val snapshot: String,
    )

    @Serializable
    data class Version(
        val id: String,
        val type: String,
        val url: String,
        val time: String,
        val releaseTime: String,
        val sha1: String,
        val complianceLevel: Int,
    )

    @Serializable
    data class VersionManifestV2(
        val latest: LatestVersions,
        val versions: List<Version>,
    )

    @Serializable
    data class VersionDownload(
        val sha1: String,
        val size: Int,
        val url: String,
    )

    @Serializable
    data class VersionDownloads(
        val client: VersionDownload,
        val client_mappings: VersionDownload,
        val server: VersionDownload,
        val server_mappings: VersionDownload,
    )

    @Serializable
    data class VersionManifest(
        val downloads: VersionDownloads,
    )
}
