package net.nosadnile.gradle.serverhelper.util

import net.nosadnile.gradle.serverhelper.api.PaperAPI
import net.nosadnile.gradle.serverhelper.dsl.ServerType
import org.gradle.api.AntBuilder
import java.io.File

class JarHelper {
    companion object Helper {
        @JvmStatic
        fun downloadFile(ant: AntBuilder, url: String, dest: File) {
            ant.invokeMethod("get", mapOf("src" to url, "dest" to dest))
        }

        @JvmStatic
        fun getServerJar(serverType: ServerType, minecraftVersion: String): String? {
            return when (serverType) {
                ServerType.PAPER -> PaperAPI.getServerJar("paper", minecraftVersion)
                ServerType.FOLIA -> PaperAPI.getServerJar("folia", minecraftVersion)
                ServerType.PURPUR -> PurpurVersionHelper.getServerJar(minecraftVersion)
                ServerType.WATERFALL -> PaperAPI.getLatestServerJar("waterfall")
                ServerType.VELOCITY -> PaperAPI.getLatestServerJar("velocity")

                else -> null
            }
        }
    }
}