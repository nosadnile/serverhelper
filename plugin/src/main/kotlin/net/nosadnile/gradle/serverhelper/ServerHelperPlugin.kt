package net.nosadnile.gradle.serverhelper

import net.nosadnile.gradle.serverhelper.dsl.ServerHelperExtension
import net.nosadnile.gradle.serverhelper.tasks.CopyJarTask
import net.nosadnile.gradle.serverhelper.tasks.RunServerTask
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class ServerHelperPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("serverHelper", ServerHelperExtension::class.java)

        project.tasks.register("copyJar", CopyJarTask::class.java) { task ->
            task.getConfig().set(extension)
        }

        project.tasks.register("runServer", RunServerTask::class.java) { task ->
            task.getConfig().set(extension)
        }
    }
}
