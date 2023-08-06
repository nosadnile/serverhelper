package net.nosadnile.gradle.serverhelper.tasks

import kotlinx.coroutines.*
import net.nosadnile.gradle.serverhelper.api.ConfigUtil
import net.nosadnile.gradle.serverhelper.api.proxy.DownstreamSetup
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

@Suppress("DuplicatedCode")
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
        return ext.serverDirectory.get().asFile.toPath() ?: project.rootDir.resolve("run").toPath()
    }

    @TaskAction
    fun launchServer() = runBlocking {
        val ext = getConfig().get()
        val servers = ArrayList<Job>()

        val jarUrl = JarHelper.getServerJar(ext.serverType.get(), ext.minecraftVersion.get()) ?: ""
        val serverDirectory = getServerDir(ext).createDirectories()
        val jarName = ext.jarName.get()
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

        if (ext.eula.get()) {
            val eulaFile = serverDirectory.resolve("eula.txt")

            if (eulaFile.exists().not() || eulaFile.readText().contains("eula=true").not()) {
                eulaFile.writeText("eula=true")
            }
        }

        if (ext.serverType.get().proxy) {
            if (ext.proxy.serverType.get().proxy) {
                throw IllegalArgumentException("Cannot use a proxy as a downstream server!")
            }

            ext.serverType.get().setup?.setupConfig(serverDirectory, ext.proxy)

            DownstreamSetup().setupConfig(serverDirectory, ext.proxy)

            for (server in ConfigUtil.toMap(ext.proxy.servers.get())) {
                val downstreamJarName = ext.proxy.jarName.get()
                val downstreamJarUrl = JarHelper.getServerJar(ext.proxy.serverType.get(), ext.proxy.minecraftVersion.get()) ?: ""
                val downstreamServerDirectory = serverDirectory.resolve("downstream").resolve(server.key)
                val downstreamJarFile = downstreamServerDirectory.resolve(downstreamJarName)
                val downstreamJarVersionFile = downstreamServerDirectory.resolve("$downstreamJarName.txt")

                val downstreamDownloadMessage = when {
                    downstreamJarFile.exists().not() -> ""
                    downstreamJarVersionFile.exists().not() || downstreamJarVersionFile.readText() != downstreamJarUrl -> "(Auto-Refresh)"
                    else -> null
                }

                if (downstreamDownloadMessage != null) {
                    logger.lifecycle(
                        """
                            Download Jar $downstreamDownloadMessage
                                url : $downstreamJarUrl
                                dest : ${downstreamJarFile.toAbsolutePath()}
                        """.trimIndent()
                    )

                    JarHelper.downloadFile(ant, downstreamJarUrl, downstreamJarFile.toFile())
                    downstreamJarVersionFile.writeText(downstreamJarUrl)
                }

                if (ext.proxy.eula.get()) {
                    val eulaFile = downstreamServerDirectory.resolve("eula.txt")

                    if (eulaFile.exists().not() || eulaFile.readText().contains("eula=true").not()) {
                        eulaFile.writeText("eula=true")
                    }
                }
            }

            for (server in ConfigUtil.toMap(ext.proxy.servers.get())) {
                val downstreamJarName = ext.proxy.jarName.get()
                val downstreamServerDirectory = serverDirectory.resolve("downstream").resolve(server.key)
                val downstreamJarFile = downstreamServerDirectory.resolve(downstreamJarName)

                val task = launch(Dispatchers.IO) {
                    project.javaexec {
                        it.run {
                            executable = "${System.getProperty("java.home")}/bin/java"

                            mainClass.set("-jar")

                            jvmArgs(ext.proxy.jvmArgs.get())

                            val args = mutableListOf<String>()

                            args.add(downstreamJarFile.toAbsolutePath().toString())
                            args.addAll(ext.proxy.serverArgs.get())

                            if (ext.proxy.nogui.get()) {
                                args.add("nogui")
                            }

                            args(args)

                            workingDir = downstreamServerDirectory.toFile()
                            standardInput = System.`in`

                            logger.lifecycle(commandLine.joinToString(" "))
                        }
                    }
                }

                servers.add(task)
            }
        }

        val task = launch(Dispatchers.IO) {
            project.javaexec {
                it.run {
                    executable = "${System.getProperty("java.home")}/bin/java"

                    mainClass.set("-jar")

                    jvmArgs(ext.jvmArgs.get())

                    val args = mutableListOf<String>()

                    args.add(jarFile.toAbsolutePath().toString())
                    args.addAll(ext.serverArgs.get())

                    if (ext.nogui.get()) {
                        args.add("nogui")
                    }

                    args(args)

                    workingDir = serverDirectory.toFile()
                    standardInput = System.`in`

                    logger.lifecycle(commandLine.joinToString(" "))
                }
            }
        }

        servers.add(task)
        servers.joinAll()
    }
}