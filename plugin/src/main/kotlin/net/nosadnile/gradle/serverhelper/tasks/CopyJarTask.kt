package net.nosadnile.gradle.serverhelper.tasks

import net.nosadnile.gradle.serverhelper.dsl.ServerHelperExtension
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.nio.file.Path
import kotlin.io.path.createDirectories

abstract class CopyJarTask : DefaultTask() {
    init {
        group = "minecraft"
        description = "Copy the JAR to the plugins folder."
    }

    init {
        dependsOn.add("build")
    }

    @Input
    abstract fun getConfig(): Property<ServerHelperExtension>

    private fun getServerDir(ext: ServerHelperExtension): Path {
        return ext.getServerDirectory().get().asFile.toPath() ?: project.rootDir.resolve("run").toPath()
    }

    @TaskAction
    fun copyJar() {
        val ext = getConfig().get()

        val serverDirectory = getServerDir(ext).createDirectories()
        val jars = project.buildDir.resolve("libs").listFiles()

        var pluginJar = jars?.get(0)?.absoluteFile

        for (jar in jars!!) {
            if (jar.name.contains("-all")) {
                pluginJar = jar.absoluteFile

                break
            }
        }

        val outJar = serverDirectory.resolve("plugins").createDirectories().resolve(pluginJar!!.name).toFile()

        if (outJar.exists()) {
            outJar.delete()
        }

        pluginJar.copyTo(outJar)
    }
}