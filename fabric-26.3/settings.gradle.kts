pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        maven("${rootDir.parentFile}/deps")
        maven("https://maven.fabricmc.net/")
        maven("https://maven.terraformersmc.com/releases/")
        maven("https://maven.blamejared.com/")
        maven("https://maven.ladysnake.org/releases")
        mavenCentral()
    }
}

rootProject.name = "cobblemon-fabric-26.3"
