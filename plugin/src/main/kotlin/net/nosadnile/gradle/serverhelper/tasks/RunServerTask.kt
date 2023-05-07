package net.nosadnile.gradle.serverhelper.tasks

import net.nosadnile.gradle.serverhelper.dsl.ServerHelperExtension
import net.nosadnile.gradle.serverhelper.util.JarHelper
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

abstract class RunServerTask : DefaultTask() {
    init {
        group = "minecraft"
        description = "Launch a Minecraft Server"
    }

    init {
        dependsOn.add("build")
        dependsOn.add("copyJar")
    }

    @Input
    abstract fun getConfig(): Property<ServerHelperExtension>

    private fun getServerDir(ext: ServerHelperExtension): Path {
        return ext.getServerDirectory().get().asFile.toPath() ?: project.rootDir.resolve("run").toPath()
    }

    @TaskAction
    fun launchServer() {
        val ext = getConfig().get()

        val jarUrl = JarHelper.getServerJar(ext.finalServerType.get(), ext.finalMinecraftVersion.get()) ?: ""
        val serverDirectory = getServerDir(ext).createDirectories()
        val jarName = ext.finalJarName.get()
        val jarFile = serverDirectory.resolve(jarName)
        val jarVersionFile = serverDirectory.resolve("$jarName.txt")

        val downloadMessage = when {
            jarFile.exists().not() -> ""
            jarVersionFile.exists().not() || jarVersionFile.readText() != jarUrl -> "(Auto-Refresh)"
            else -> null
        }

        if (downloadMessage != null) {
            logger.lifecycle(
                """
                    Download Jar $downloadMessage
                        url : $jarUrl
                        dest : ${jarFile.toAbsolutePath()}
                """.trimIndent()
            )

            JarHelper.downloadFile(ant, jarUrl, jarFile.toFile())
            jarVersionFile.writeText(jarUrl)
        }

        if (ext.finalEula.get()) {
            val eulaFile = serverDirectory.resolve("eula.txt")

            if (eulaFile.exists().not() || eulaFile.readText().contains("eula=true").not()) {
                eulaFile.writeText("eula=true")
            }
        }

        project.javaexec {
            it.run {
                mainClass.set("-jar")

                jvmArgs(ext.finalJvmArgs.get())

                val args = mutableListOf<String>()

                args.add(jarFile.toAbsolutePath().toString())
                args.addAll(ext.finalServerArgs.get())

                if (ext.finalNogui.get()) {
                    args.add("nogui")
                }

                args(args)

                workingDir = serverDirectory.toFile()
                standardInput = System.`in`
                logger.lifecycle(commandLine.joinToString(" "))
            }
        }
    }
}