import net.nosadnile.gradle.serverhelper.dsl.ServerType

plugins {
    id("java")
    id("net.nosadnile.gradle.serverhelper") version "1.2.0"
}

group = "net.nosadnile.gradle"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

serverHelper {
    getEula().set(true)
    getServerType().set(ServerType.PURPUR)
    getServerDirectory().set(project.rootDir.resolve("run"))
    getMinecraftVersion().set("1.19.4")
}
