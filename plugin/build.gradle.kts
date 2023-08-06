plugins {
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.serialization") version "1.8.21"

    id("maven-publish")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.21.0"
}

group = "net.nosadnile.gradle"
version = "1.6.0"

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("stdlib-jdk7"))

    implementation("com.akuleshov7:ktoml-core:0.5.0")
    implementation("com.charleskorn.kaml:kaml:0.55.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
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
            url = uri("https://repo.nosadnile.net/releases")

            credentials {
                username = System.getenv("MAVEN_REPO_USER")
                password = System.getenv("MAVEN_REPO_TOKEN")
            }
        }
    }
}
