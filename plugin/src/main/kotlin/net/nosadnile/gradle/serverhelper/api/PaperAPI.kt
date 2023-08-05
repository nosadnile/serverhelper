package net.nosadnile.gradle.serverhelper.api

import io.github.z4kn4fein.semver.Version
import io.github.z4kn4fein.semver.toVersion
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.nosadnile.gradle.serverhelper.schema.PaperMeta
import java.net.URL

class PaperAPI {
    companion object Helper {
        private val json = Json { ignoreUnknownKeys = true }

        @JvmStatic
        fun getServerJar(project: String, version: String): String {
            val buildId = getLatestProjectBuildId(project, version.toVersion())

            return getProjectDownload(project, version.toVersion(), buildId)
        }

        @JvmStatic
        fun getLatestServerJar(project: String): String {
            val version = getProjectLatestVersion(project)
            val buildId = getLatestProjectBuildId(project, version)

            return getProjectDownload(project, version, buildId)
        }

        @JvmStatic
        fun getProject(project: String): PaperMeta.Project {
            val projectUrl = "https://papermc.io/api/v2/projects/$project"
            val projectJson = URL(projectUrl).readText()

            return json.decodeFromString<PaperMeta.Project>(projectJson)
        }

        @JvmStatic
        fun getProjectVersions(project: String): List<Version> {
            return getProject(project).versions.map { it.toVersion() }
        }

        @JvmStatic
        fun getProjectLatestVersion(project: String): Version {
            return getProjectVersions(project).max()
        }

        @JvmStatic
        fun getProjectBuildIds(project: String, version: Version): List<Int> {
            val buildsUrl = "https://papermc.io/api/v2/projects/$project/versions/$version/builds"
            val buildsJson = URL(buildsUrl).readText()
            val builds = json.decodeFromString<PaperMeta.VersionBuilds>(buildsJson)

            return builds.builds.map { it.build }
        }

        @JvmStatic
        fun getLatestProjectBuildId(project: String, version: Version): Int {
            return getProjectBuildIds(project, version).max()
        }

        @JvmStatic
        fun getProjectBuild(project: String, version: Version, id: Int): PaperMeta.VersionBuild {
            val buildUrl = "https://papermc.io/api/v2/projects/$project/versions/$version/builds/$id"
            val buildJson = URL(buildUrl).readText()

            return json.decodeFromString<PaperMeta.VersionBuild>(buildJson)
        }

        @JvmStatic
        fun getProjectFile(project: String, version: Version, id: Int): String? {
            val build = getProjectBuild(project, version, id)
            val file = build.downloads["application"]

            return file?.name
        }

        @JvmStatic
        fun getProjectDownload(project: String, version: Version, id: Int): String {
            val file = getProjectFile(project, version, id)

            return "https://papermc.io/api/v2/projects/$project/versions/$version/builds/$id/downloads/$file"
        }
    }
}