package net.nosadnile.gradle.serverhelper.util

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
                ServerType.VANILLA -> MinecraftVersionHelper.getMinecraftServerJar(minecraftVersion)
                ServerType.FABRIC -> FabricVersionHelper.getServerJar(minecraftVersion)
                ServerType.PAPER -> PaperVersionHelper.getServerJar(minecraftVersion)
                ServerType.PURPUR -> PurpurVersionHelper.getServerJar(minecraftVersion)
                ServerType.VELOCITY -> VelocityVersionHelper.getServerJar(minecraftVersion)
                ServerType.WATERFALL -> WaterfallVersionHelper.getServerJar(minecraftVersion)

                else -> null
            }
        }
    }
}