package net.nosadnile.gradle.serverhelper.api.proxy

import net.nosadnile.gradle.serverhelper.dsl.ProxyConfig
import java.nio.file.Path

interface IProxySetup<T> {
    fun setupServers(config: T, servers: Map<String, Int>): T
    fun setupConfig(dir: Path, config: ProxyConfig)
}