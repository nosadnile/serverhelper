package net.nosadnile.gradle.serverhelper.dsl

import net.nosadnile.gradle.serverhelper.api.PistonAPI
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import javax.inject.Inject

open class ServerHelperExtension @Inject constructor(private val project: Project) {
    val serverType: Property<ServerType> = project.objects.property(ServerType::class.java).convention(ServerType.PAPER)
    val minecraftVersion: Property<String> = project.objects.property(String::class.java).convention(PistonAPI.getLatestMinecraftVersion())
    val jarName: Property<String> = project.objects.property(String::class.java).convention("server.jar")
    val serverDirectory: RegularFileProperty = project.objects.fileProperty()
    val jvmArgs: ListProperty<String> = project.objects.listProperty(String::class.java).convention(listOf())
    val serverArgs: ListProperty<String> = project.objects.listProperty(String::class.java).convention(listOf())
    val nogui: Property<Boolean> = project.objects.property(Boolean::class.java).convention(true)
    val eula: Property<Boolean> = project.objects.property(Boolean::class.java).convention(false)
    var proxy: ProxyConfig = ProxyConfig(project)

    fun proxy(action: Action<in ProxyConfig>) {
        val proxy = ProxyConfig(project)

        action.execute(proxy)
        this.proxy = proxy
    }

    fun print() {
        println("ServerType: ${serverType.getOrElse(ServerType.PAPER)}")
        println("MinecraftVersion: ${minecraftVersion.getOrElse(PistonAPI.getLatestMinecraftVersion())}")
        println("JarName: ${jarName.getOrElse("server.jar")}")
        println("ServerDirectory: ${serverDirectory.get()}")
        println("JvmArgs: ${jvmArgs.getOrElse(listOf())}")
        println("ServerArgs: ${serverArgs.getOrElse(listOf())}")
        println("Nogui: ${nogui.getOrElse(true)}")
        println("Eula: ${eula.getOrElse(false)}")

        proxy.print()
    }
}
