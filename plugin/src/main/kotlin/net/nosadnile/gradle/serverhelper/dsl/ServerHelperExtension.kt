package net.nosadnile.gradle.serverhelper.dsl

import net.nosadnile.gradle.serverhelper.api.PistonAPI
import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider

abstract class ServerHelperExtension(project: Project) : ServerHelperExtensionAPI {
    private val serverType: Property<ServerType>
    private val minecraftVersion: Property<String>
    private val jarName: Property<String>
    private val serverDirectory: RegularFileProperty
    private val jvmArgs: ListProperty<String>
    private val serverArgs: ListProperty<String>
    private val nogui: Property<Boolean>
    private val eula: Property<Boolean>

    init {
        this.serverType = project.objects.property(ServerType::class.java)
        this.serverType.finalizeValueOnRead()
        this.minecraftVersion = project.objects.property(String::class.java)
        this.minecraftVersion.finalizeValueOnRead()
        this.jarName = project.objects.property(String::class.java)
        this.jarName.finalizeValueOnRead()
        this.serverDirectory = project.objects.fileProperty()
        this.serverDirectory.finalizeValueOnRead()
        this.jvmArgs = project.objects.listProperty(String::class.java)
        this.jvmArgs.finalizeValueOnRead()
        this.serverArgs = project.objects.listProperty(String::class.java)
        this.serverArgs.finalizeValueOnRead()
        this.nogui = project.objects.property(Boolean::class.java)
        this.nogui.finalizeValueOnRead()
        this.eula = project.objects.property(Boolean::class.java)
        this.eula.finalizeValueOnRead()
    }

    val finalServerType: Provider<ServerType> get() = getServerType().orElse(ServerType.PAPER)
    val finalMinecraftVersion: Provider<String> get() = getMinecraftVersion().orElse(PistonAPI.getLatestMinecraftVersion())
    val finalJarName: Provider<String> get() = getJarName().orElse("server.jar")
    val finalJvmArgs: Provider<List<String>> get() = getJvmArgs().orElse(listOf())
    val finalServerArgs: Provider<List<String>> get() = getServerArgs().orElse(listOf())
    val finalNogui: Provider<Boolean> get() = getNogui().orElse(true)
    val finalEula: Provider<Boolean> get() = getEula().orElse(false)
}
