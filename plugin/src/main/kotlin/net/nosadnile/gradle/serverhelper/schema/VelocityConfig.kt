package net.nosadnile.gradle.serverhelper.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class VelocityConfig {
    @Serializable
    data class VelocityConfigFile(
        @SerialName("config-version")
        val configVersion: String,

        val bind: String,
        val motd: String,

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

        val servers: Map<String, Either<String, List<String>>>,
    )
}