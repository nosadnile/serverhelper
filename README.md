# ServerHelper

![Version](https://repo.nosadnile.net/api/badge/latest/releases/net/nosadnile/gradle/serverhelper/net.nosadnile.gradle.serverhelper.gradle.plugin?color=40c14a&name=ServerHelper&prefix=v)

A Gradle plugin for the development of Minecraft server plugins and Proxy server plugins.

## Usage

```kts
// settings.gradle.kts

pluginManagement {
    repositories {
        maven {
            name = "nosadnile-maven"
            url = uri("https://repo.nosadnile.net/releases")
        }
    }
}
```

```kts
// build.gradle.kts

plugins {
    // Check the link below for the latest version.
    // https://repo.nosadnile.net/#/releases/net/nosadnile/gradle/serverhelper/net.nosadnile.gradle.serverhelper.gradle.plugin
    id("net.nosadnile.gradle.serverhelper") version "1.6.0"
}
```

## Tasks

There are three tasks, `copyJar`, `runServer`, and `printConfig`.

`copyJar` will copy the built JAR of your plugin into your server's plugins directory.

`runServer` will start the server, and any downstream servers for a proxy at the same time.

`printConfig` will print the current configuration of the gradle plugin. Useful when developing the plugin.

## Configuration

All configuration values are technically optional, as defaults have been set for all of them, however you likely will want to change some of these values.

```kts
// build.gradle.kts

import net.nosadnile.gradle.serverhelper.dsl.ServerType

// ...

serverHelper {
    // Do you agree to the EULA? (Default: false)
    eula.set(true)
    
    // What kind of server are you developing for? (Default: ServerType.PAPER)
    serverType.set(/* PAPER | FOLIA | PURPUR | WATERFALL | VELOCITY */ ServerType.PAPER)
    
    // What Minecraft version should be targeted? (Default: Fetch latest version)
    minecraftVersion.set("1.20.1")
    
    // What should the server JAR file be called when ran? (Default: "server.jar")
    jarName.set("server.jar")
    
    // What directory should the server be in? (Default: "[Project Directory]/run")
    serverDirectory.set(file("..."))
    
    // What extra JVM args should be applied? (Default: None)
    jvmArgs.set(listOf("-Xmx4096M", "-Xms128M", "..."))
    
    // What server args should be added? (Default: None)
    serverArgs.set(listOf("..."))
    
    // Should the server GUI not be shown? (Default: true)
    nogui.set(true)
    
    // If this is a proxy server, how about downstream servers?
    proxy {
        // Do you agree to the EULA for all the downstream servers? (Default: false)
        eula.set(true)
        
        // What kind of server should downstream servers use? (Default: ServerType.PAPER)
        serverType.set(/* PAPER | FOLIA | PURPUR | WATERFALL | VELOCITY */ ServerType.PAPER)

        // What Minecraft version should downstream servers use? (Default: Fetch latest version)
        minecraftVersion.set("1.20.1")

        // What should the downstream server JAR file be called when ran? (Default: "server.jar")
        jarName.set("server.jar")

        // What extra JVM args should be applied to downstream servers? (Default: None)
        jvmArgs.set(listOf("-Xmx4096M", "-Xms128M", "..."))

        // What server args should be added to downstream servers? (Default: None)
        serverArgs.set(listOf("..."))

        // Should the server GUI not be shown for each downstream server? (Default: true)
        nogui.set(true)
        
        // What should the downstream servers be when testing? (Function: (name: String, port: Int) -> void)
        server("lobby", 25565)
        server("smp", 25566)
        server("creative", 25567)
    }
}
```
