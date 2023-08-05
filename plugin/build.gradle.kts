plugins {
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.serialization") version "1.8.21"

    id("maven-publish")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.21.0"
}

group = "net.nosadnile.gradle"
version = "1.5.0"

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("stdlib-jdk7"))

    implementation("com.akuleshov7:ktoml-core:0.5.0")
    implementation("com.akuleshov7:ktoml-file:0.5.0")
    implementation("io.github.z4kn4fein:semver:1.4.2")
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
    jvmToolchain(17)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/nosadnile/serverhelper")

            credentials {
                username = System.getProperty("github.user") ?: System.getenv("GITHUB_ACTOR")
                password = System.getProperty("github.token") ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
