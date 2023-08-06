package net.nosadnile.gradle.serverhelper.api.proxy

import net.nosadnile.gradle.serverhelper.api.ConfigUtil
import net.nosadnile.gradle.serverhelper.dsl.ProxyConfig
import net.nosadnile.gradle.serverhelper.schema.WaterfallConfig
import java.nio.file.Path
import kotlin.io.path.createFile
import kotlin.io.path.deleteIfExists
import kotlin.io.path.readText
import kotlin.io.path.writeText

class WaterfallProxySetup : IProxySetup<WaterfallConfig.Config> {
    override fun setupServers(config: WaterfallConfig.Config, servers: Map<String, Int>): WaterfallConfig.Config {
        config.servers.clear()

        if (config.listeners.size >= 1) {
            config.listeners[0].priorities.clear()
        }

        for (server in servers) {
            val (key, value) = server
            val address = "localhost:$value"
            val motd = "&1Just another Waterfall - Forced Host"
            val item = WaterfallConfig.Server(motd, address, false)

            config.servers[key] = item
            config.listeners[0].priorities.add(key)
        }

        return config
    }

    override fun setupConfig(dir: Path, config: ProxyConfig) {
        val servers = ConfigUtil.toMap(config.servers.get())
        val filePath = dir.resolve(WaterfallConfig.configFile)

        filePath.deleteIfExists()
        filePath.createFile()

        WaterfallProxySetup::class.java.getResource("/default-waterfall.yml")?.let { filePath.writeText(it.readText()) }

        val serverConfig = setupServers(WaterfallConfig.serializer.deserialize(filePath.readText()), servers)

        filePath.writeText(WaterfallConfig.serializer.serialize(serverConfig))
    }
}