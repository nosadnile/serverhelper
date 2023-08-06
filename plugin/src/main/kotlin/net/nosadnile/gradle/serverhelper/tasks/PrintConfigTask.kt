package net.nosadnile.gradle.serverhelper.tasks

import net.nosadnile.gradle.serverhelper.dsl.ServerHelperExtension
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class PrintConfigTask : DefaultTask() {
    init {
        group = "minecraft"
        description = "Print the current config.."
    }

    @Input
    abstract fun getConfig(): Property<ServerHelperExtension>

    @TaskAction
    fun printConfig() {
        val ext = getConfig().get()

        ext.print()
    }
}