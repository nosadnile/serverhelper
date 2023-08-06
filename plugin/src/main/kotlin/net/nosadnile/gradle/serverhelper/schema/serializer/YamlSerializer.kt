package net.nosadnile.gradle.serverhelper.schema.serializer

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.KSerializer

class YamlSerializer<T>(private val serializer: KSerializer<T>) : ISerializer<T> {
    override fun deserialize(content: String): T {
        return Yaml.default.decodeFromString(serializer, content)
    }

    override fun serialize(data: T): String {
        return Yaml.default.encodeToString(serializer, data)
    }
}