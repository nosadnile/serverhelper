package net.nosadnile.gradle.serverhelper.schema

import kotlinx.serialization.Serializable

class PaperMeta {
    @Serializable
    data class Builds(val builds: List<Int>)

    @Serializable
    data class Project(
        val project_id: String,
        val project_name: String,
        val version_groups: List<String>,
        val versions: List<String>,
    )

    @Serializable
    data class VersionBuilds(
        val project_id: String,
        val project_name: String,
        val version: String,
        val builds: List<VersionBuild>,
    )

    @Serializable
    data class VersionChanges(
        val commit: String,
        val summary: String,
        val message: String,
    )

    @Serializable
    data class VersionFile(
        val name: String,
        val sha256: String,
    )

    @Serializable
    data class VersionBuild(
        val build: Int,
        val time: String,
        val promoted: Boolean,
        val changes: List<VersionChanges>,

        /**
         * Most likely will have the key "application",
         * and possibly the key "mojang-mappings". The
         * one we want is likely "application".
         */
        val downloads: Map<String, VersionFile>,
    )
}