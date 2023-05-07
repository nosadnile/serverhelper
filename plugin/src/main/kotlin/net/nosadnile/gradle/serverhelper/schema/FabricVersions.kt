package net.nosadnile.gradle.serverhelper.schema

import kotlinx.serialization.Serializable

class FabricVersions {
    @Serializable
    data class FabricInstallerVersion(
        val url: String,
        val maven: String,
        val version: String,
        val stable: Boolean
    )

    @Serializable
    data class FabricLoaderVersion(
        val separator: String,
        val maven: String,
        val version: String,
        val stable: Boolean
    )
}
