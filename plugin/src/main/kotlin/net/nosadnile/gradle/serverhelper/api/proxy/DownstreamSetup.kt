package net.nosadnile.gradle.serverhelper.api.proxy

import net.nosadnile.gradle.serverhelper.api.ConfigUtil
import net.nosadnile.gradle.serverhelper.dsl.ProxyConfig
import java.nio.file.Path
import kotlin.io.path.*

class DownstreamSetup : IProxySetup<Path> {
    override fun setupServers(config: Path, servers: Map<String, Int>): Path {
        // config is actually the proxy's directory
        for (server in servers) {
            val regex = Regex("server-port=\\d+", RegexOption.IGNORE_CASE)
            val dir = config.resolve("downstream").resolve(server.key)

            dir.createDirectories()

            val propertiesFile = dir.resolve("server.properties")

            if (!propertiesFile.exists()) {
                val propertiesString = "server-port=${server.value}"

                propertiesFile.createFile()
                propertiesFile.writeText(propertiesString)
            } else {
                var propertiesString = propertiesFile.readText()

                propertiesString = propertiesString.replace(regex, "server-port=${server.value}")

                propertiesFile.deleteIfExists()
                propertiesFile.createFile()
                propertiesFile.writeText(propertiesString)
            }
        }

        return config
    }

    override fun setupConfig(dir: Path, config: ProxyConfig) {
        setupServers(dir, ConfigUtil.toMap(config.servers.get()))
    }
}