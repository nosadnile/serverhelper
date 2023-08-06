package net.nosadnile.gradle.serverhelper.schema.serializer

interface ISerializer<T> {
    fun deserialize(content: String): T
    fun serialize(data: T): String
}
