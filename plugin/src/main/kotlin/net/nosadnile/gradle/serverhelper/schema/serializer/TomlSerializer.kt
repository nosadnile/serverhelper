package net.nosadnile.gradle.serverhelper.schema.serializer

import com.akuleshov7.ktoml.Toml
import com.akuleshov7.ktoml.TomlIndentation
import com.akuleshov7.ktoml.TomlInputConfig
import com.akuleshov7.ktoml.TomlOutputConfig
import kotlinx.serialization.KSerializer

class TomlSerializer<T>(private val serializer: KSerializer<T>) : ISerializer<T> {
    val toml = Toml(
        inputConfig = TomlInputConfig(
            ignoreUnknownNames = true,
            allowEmptyValues = true,
            allowNullValues = true,
            allowEscapedQuotesInLiteralStrings = true,
            allowEmptyToml = true,
        ),

        outputConfig = TomlOutputConfig(
            indentation = TomlIndentation.FOUR_SPACES,
        ),
    )

    override fun deserialize(content: String): T {
        return toml.decodeFromString(serializer, content)
    }

    override fun serialize(data: T): String {
        return toml.encodeToString(serializer, data)
    }
}