package net.nosadnile.gradle.serverhelper.schema

import kotlinx.serialization.Serializable

class PurpurMeta {
    @Serializable
    data class PurpurBuilds(
        val all: List<String>,
        val latest: String
    )

    @Serializable
    data class PurpurVersion(
        val builds: PurpurBuilds,
        val project: String,
        val version: String
    )
}