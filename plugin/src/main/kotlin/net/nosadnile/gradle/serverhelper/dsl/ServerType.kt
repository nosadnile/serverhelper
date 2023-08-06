package net.nosadnile.gradle.serverhelper.dsl

import net.nosadnile.gradle.serverhelper.api.proxy.IProxySetup
import net.nosadnile.gradle.serverhelper.api.proxy.VelocityProxySetup
import net.nosadnile.gradle.serverhelper.api.proxy.WaterfallProxySetup

enum class ServerType(val proxy: Boolean, val setup: IProxySetup<*>?) {
    PAPER(false, null),
    FOLIA(false, null),
    PURPUR(false, null),
    WATERFALL(true, WaterfallProxySetup()),
    VELOCITY(true, VelocityProxySetup()),
}