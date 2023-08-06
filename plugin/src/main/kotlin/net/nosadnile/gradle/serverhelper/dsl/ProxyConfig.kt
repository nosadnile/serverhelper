package net.nosadnile.gradle.serverhelper.dsl

import net.nosadnile.gradle.serverhelper.api.PistonAPI
import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
open class ProxyConfig @Inject constructor(project: Project) {
    val servers: ListProperty<Pair<String, Int>> = project.objects.listProperty(Pair::class.java).convention(listOf()) as ListProperty<Pair<String, Int>>
    val serverType: Property<ServerType> = project.objects.property(ServerType::class.java).convention(ServerType.PAPER)
    val minecraftVersion: Property<String> = project.objects.property(String::class.java).convention(PistonAPI.getLatestMinecraftVersion())
    val jarName: Property<String> = project.objects.property(String::class.java).convention("server.jar")
    val jvmArgs: ListProperty<String> = project.objects.listProperty(String::class.java).convention(listOf())
    val serverArgs: ListProperty<String> = project.objects.listProperty(String::class.java).convention(listOf())
    val nogui: Property<Boolean> = project.objects.property(Boolean::class.java).convention(true)
    val eula: Property<Boolean> = project.objects.property(Boolean::class.java).convention(false)

    fun server(name: String, port: Int) {
        this.servers.add(Pair(name, port))
    }

    fun print() {
        println("[proxy] ServerType: ${serverType.getOrElse(ServerType.PAPER)}")
        println("[proxy] MinecraftVersion: ${minecraftVersion.getOrElse(PistonAPI.getLatestMinecraftVersion())}")
        println("[proxy] JarName: ${jarName.getOrElse("server.jar")}")
        println("[proxy] JvmArgs: ${jvmArgs.getOrElse(listOf())}")
        println("[proxy] ServerArgs: ${serverArgs.getOrElse(listOf())}")
        println("[proxy] Nogui: ${nogui.getOrElse(true)}")
        println("[proxy] Eula: ${eula.getOrElse(false)}")

        for (server in servers.getOrElse(listOf())) {
            println("[proxy] Server: ${server.first} @ localhost:${server.second}")
        }
    }
}