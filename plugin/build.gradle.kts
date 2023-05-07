plugins {
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.serialization") version "1.8.21"

    id("maven-publish")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.21.0"
}

group = "net.nosadnile.gradle"
version = "1.0.0"

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("stdlib-jdk7"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
}

gradlePlugin {
    plugins {
        create("serverHelper") {
            id = "net.nosadnile.gradle.serverhelper"
            implementationClass = "net.nosadnile.gradle.serverhelper.ServerHelperPlugin"
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}
