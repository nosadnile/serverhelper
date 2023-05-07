package net.nosadnile.gradle.serverhelper.schema

import kotlinx.serialization.Serializable

class PaperMeta {
    @Serializable
    data class Builds(val builds: List<Int>)
}