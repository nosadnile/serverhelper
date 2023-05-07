package net.nosadnile.gradle.serverhelper.dsl

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.options.Option

interface ServerHelperExtensionAPI {
    @Input
    @Optional
    @Option(
        option = "serverType",
        description = "The type of the server to run with (spigot, paper, etc). Default is paper."
    )
    fun getServerType(): Property<ServerType>

    @Input
    @Optional
    @Option(
        option = "minecraftVersion",
        description = "The Minecraft version to run with. Default is the latest version."
    )
    fun getMinecraftVersion(): Property<String>

    @Input
    @Optional
    @Option(option = "jarName", description = "The server JAR's name.")
    fun getJarName(): Property<String>

    @Input
    @Optional
    @Option(
        option = "serverDirectory",
        description = "The directory to run the server in. Default is \"[projectRoot]/run\"."
    )
    fun getServerDirectory(): RegularFileProperty

    @Input
    @Optional
    @Option(option = "jvmArgs", description = "Extra JVM args for the server. Default are \"[]\".")
    fun getJvmArgs(): ListProperty<String>

    @Input
    @Optional
    @Option(option = "serverArgs", description = "Extra args after the JAR call for the server. Default are \"[]\".")
    fun getServerArgs(): ListProperty<String>

    @Input
    @Optional
    @Option(option = "nogui", description = "Whether or not to show the server GUI on start. Default is true.")
    fun getNogui(): Property<Boolean>

    @Input
    @Optional
    @Option(option = "eula", description = "Whether or not you agree to the Minecraft EULA. Default is false.")
    fun getEula(): Property<Boolean>
}
