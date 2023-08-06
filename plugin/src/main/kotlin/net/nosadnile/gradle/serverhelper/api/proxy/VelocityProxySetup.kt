package net.nosadnile.gradle.serverhelper.api.proxy

import net.nosadnile.gradle.serverhelper.api.ConfigUtil
import net.nosadnile.gradle.serverhelper.dsl.ProxyConfig
import net.nosadnile.gradle.serverhelper.schema.VelocityConfig
import java.nio.file.Path
import kotlin.io.path.createFile
import kotlin.io.path.deleteIfExists
import kotlin.io.path.readText
import kotlin.io.path.writeText

class VelocityProxySetup : IProxySetup<VelocityConfig.Config> {
    override fun setupServers(config: VelocityConfig.Config, servers: Map<String, Int>): VelocityConfig.Config {
        config.servers.clear()

        for (server in servers) {
            val (key, value) = server
            val address = "127.0.0.1:$value"

            config.servers[key] = address
        }

        return config
    }

    override fun setupConfig(dir: Path, config: ProxyConfig) {
        val servers = ConfigUtil.toMap(config.servers.get())
        val filePath = dir.resolve(VelocityConfig.configFile)

        filePath.deleteIfExists()
        filePath.createFile()

        VelocityProxySetup::class.java.getResource("/default-velocity.toml")?.let { filePath.writeText(it.readText()) }

        val serverConfig = setupServers(VelocityConfig.serializer.deserialize(filePath.readText()), servers)

        filePath.writeText(VelocityConfig.serializer.serialize(serverConfig))
    }
}