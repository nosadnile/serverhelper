package net.nosadnile.gradle.serverhelper.schema

import kotlinx.serialization.Serializable
import net.nosadnile.gradle.serverhelper.schema.serializer.YamlSerializer

@Suppress("PropertyName")
class WaterfallConfig {
    companion object Helper {
        val configFile = "config.yml"
        val serializer = YamlSerializer(Config.serializer())
    }

    @Serializable
    data class Server(
        val motd: String,
        val address: String,
        val restricted: Boolean,
    )

    @Serializable
    data class Listener(
        val query_port: Int,
        val motd: String,
        val tab_list: String,
        val query_enabled: Boolean,
        val proxy_protocol: Boolean,
        val forced_hosts: MutableMap<String, String>,
        val ping_passthrough: Boolean,
        val priorities: MutableList<String>,
        val bind_local_address: Boolean,
        val host: String,
        val max_players: Int,
        val tab_size: Int,
        val force_default_server: Boolean,
    )

    @Serializable
    data class Config(
        val server_connect_timeout: Int,
        val enforce_secure_profile: Boolean,
        val remote_ping_cache: Int,
        val forge_support: Boolean,
        val player_limit: Int,
        val permissions: MutableMap<String, MutableList<String>>,
        val timeout: Int,
        val log_commands: Boolean,
        val network_compression_threshold: Int,
        val online_mode: Boolean,
        val disabled_commands: MutableList<String>,
        val servers: MutableMap<String, Server>,
        val listeners: MutableList<Listener>,
        val ip_forward: Boolean,
        val remote_ping_timeout: Int,
        val prevent_proxy_connections: Boolean,
        val groups: MutableMap<String, MutableList<String>>,
        val connection_throttle: Int,
        val stats: String,
        val connection_throttle_limit: Int,
        val log_pings: Boolean,
    )
}