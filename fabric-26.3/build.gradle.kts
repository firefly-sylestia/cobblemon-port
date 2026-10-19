plugins {
    id("net.fabricmc.fabric-loom") version "1.17.9"
    kotlin("jvm") version "2.3.20"
}

group = "com.cobblemon.mod"
version = "1.8.3+26.3-port"

val sourceRoot = rootDir.parentFile

loom {
    // The shared access widener still targets 1.21.1, so the 26.3 target keeps its own copy with
    // entries validated against the 26.3 Minecraft jar. Without this the Minecraft jar is not
    // widened and every private-field access fails.
    accessWidenerPath.set(layout.projectDirectory.file("src/main/resources/cobblemon-26.3.accesswidener"))
}


repositories {
    maven(sourceRoot.resolve("deps"))
    maven("https://maven.terraformersmc.com/releases/")
    maven("https://maven.blamejared.com/")
    maven("https://maven.ladysnake.org/releases")
    mavenCentral()
}

sourceSets {
    main {
        java.setSrcDirs(
            listOf(
                sourceRoot.resolve("common/src/main/java"),
                sourceRoot.resolve("fabric/src/main/java")
            )
        )
        resources.setSrcDirs(
            listOf(
                sourceRoot.resolve("common/src/main/resources"),
                sourceRoot.resolve("fabric/src/main/resources"),
                layout.projectDirectory.dir("src/main/resources")
            )
        )
    }
}

kotlin {
    jvmToolchain(25)
    sourceSets.main {
        kotlin.srcDirs(
            sourceRoot.resolve("common/src/main/kotlin"),
            sourceRoot.resolve("fabric/src/main/kotlin"),
            layout.projectDirectory.dir("src/main/kotlin")
        )
        kotlin.exclude(
            "**/integration/jei/**",
            "**/compat/lambdynamiclights/**",
            "**/integration/appleskin/**"
        )
    }
}

dependencies {
    minecraft("com.mojang:minecraft:26.3")
    implementation("net.fabricmc:fabric-loader:0.19.5")
    implementation("net.fabricmc.fabric-api:fabric-api:0.163.0+26.3")
    implementation("net.fabricmc:fabric-language-kotlin:1.13.10+kotlin.2.3.20")
    compileOnly("com.terraformersmc:modmenu:21.0.0")

    implementation("local.com.bedrockk:molang:1.1.20")
    implementation("org.mongodb:mongodb-driver-core:4.10.2")
    implementation("org.mongodb:mongodb-driver-sync:4.10.2")
    implementation("org.graalvm.js:js:22.3.0")
    implementation("org.graalvm.sdk:graal-sdk:22.3.0")
    implementation("org.graalvm.regex:regex:22.3.0")
    implementation("org.graalvm.truffle:truffle-api:22.3.0")
    implementation("com.ibm.icu:icu4j:71.1")

    include("net.fabricmc:fabric-language-kotlin:1.13.10+kotlin.2.3.20")
    include("local.com.bedrockk:molang:1.1.20")
    include("org.mongodb:mongodb-driver-core:4.10.2")
    include("org.mongodb:mongodb-driver-sync:4.10.2")
    include("org.graalvm.js:js:22.3.0")
    include("org.graalvm.sdk:graal-sdk:22.3.0")
    include("org.graalvm.regex:regex:22.3.0")
    include("org.graalvm.truffle:truffle-api:22.3.0")
    include("com.ibm.icu:icu4j:71.1")
}

tasks.processResources {
    exclude { it.file.canonicalFile == sourceRoot.resolve("fabric/src/main/resources/fabric.mod.json").canonicalFile }
    exclude("data/**/README.md")
}

tasks.processResources {
    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version,
            "fabric_loader_version" to "0.19.5",
            "fabric_api_version" to "0.163.0+26.3",
            "minecraft_version" to "26.3",
            "java_version" to "25"
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(25)
    options.encoding = "UTF-8"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25)
}

tasks.jar {
    archiveBaseName.set("Cobblemon-fabric-26.3")
    from(sourceRoot.resolve("LICENSE"))
}
