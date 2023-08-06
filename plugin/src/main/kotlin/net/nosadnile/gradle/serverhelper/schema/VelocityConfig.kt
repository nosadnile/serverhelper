package net.nosadnile.gradle.serverhelper.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.nosadnile.gradle.serverhelper.schema.serializer.TomlSerializer

class VelocityConfig {
    companion object Helper {
        val configFile = "velocity.toml"
        val serializer = TomlSerializer(Config.serializer())
    }

    @Serializable
    data class AdvancedConfig(
        @SerialName("compression-threshold")
        val compressionThreshold: Int,

        @SerialName("compression-level")
        val compressionLevel: Int,

        @SerialName("login-ratelimit")
        val loginRateLimit: Int,

        @SerialName("connection-timeout")
        val connectionTimeout: Int,

        @SerialName("read-timeout")
        val readTimeout: Int,

        @SerialName("haproxy-protocol")
        val haproxyProtocol: Boolean,

        @SerialName("tcp-fast-open")
        val tcpFastOpen: Boolean,

        @SerialName("bungee-plugin-message-channel")
        val bungeePluginMessageChannel: Boolean,

        @SerialName("show-ping-requests")
        val showPingRequests: Int,

        @SerialName("failover-on-unexpected-server-disconnect")
        val failoverOnUnexpectedServerDisconnect: Boolean,

        @SerialName("announce-proxy-commands")
        val announceProxyCommands: Boolean,

        @SerialName("log-command-executions")
        val logCommandExecutions: Boolean,

        @SerialName("log-player-connections")
        val logPlayerConnections: Boolean,
    )

    @Serializable
    data class QueryConfig(
        @SerialName("show-plugins")
        val showPlugins: Boolean,

        val enabled: Boolean,
        val port: Int,
        val map: String,
    )

    @Serializable
    data class Config(
        @SerialName("config-version")
        val configVersion: String,

        @SerialName("show-max-players")
        val showMaxPlayers: Int,

        @SerialName("online-mode")
        val onlineMode: Boolean,

        @SerialName("force-key-authentication")
        val forceKeyAuthentication: Boolean,

        @SerialName("prevent-client-proxy-connections")
        val preventClientProxyConnections: Boolean,

        @SerialName("player-info-forwarding-mode")
        val playerInfoForwardingMode: String,

        @SerialName("forwarding-secret-file")
        val forwardingSecretFile: String,

        @SerialName("announce-forge")
        val announceForge: Boolean,

        @SerialName("kick-existing-players")
        val kickExistingPlayers: Boolean,

        @SerialName("ping-passthrough")
        val pingPassthrough: String,

        @SerialName("enable-player-address-logging")
        val enablePlayerAddressLogging: Boolean,

        @SerialName("forced-hosts")
        val forcedHosts: MutableMap<String, MutableList<String>>,

        val bind: String,
        val motd: String,
        val servers: MutableMap<String, String>,
        val advanced: AdvancedConfig,
        val query: QueryConfig,
    )
}