import net.nosadnile.gradle.serverhelper.dsl.ServerType

plugins {
    id("java")
    id("net.nosadnile.gradle.serverhelper") version "1.5.0"
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
    eula.set(true)
    serverType.set(ServerType.VELOCITY)
    serverDirectory.set(project.rootDir.resolve("run"))
    minecraftVersion.set("1.20.1")

    proxy {
        eula.set(true)

        server("lobby", 25565)
    }
}
