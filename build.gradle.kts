plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.github.kaiquy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://libraries.minecraft.net/")
    maven("https://repo.aikar.co/content/groups/aikar/")

    maven("https://jitpack.io")
}

dependencies {
    implementation("org.jetbrains:annotations:24.0.0")

    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly("com.github.azbh111:craftbukkit-1.8.8:R")

    compileOnly(fileTree("libs"))
    implementation("com.zaxxer:HikariCP:4.0.3")
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")

    implementation (fileTree("inventory"))
    implementation("com.github.SaiintBrisson.command-framework:bukkit:1.3.1")


}

tasks {
    java {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
    compileJava { options.encoding = "UTF-8" }

    java {
        shadowJar {
            archiveFileName.set("${project.name}.jar")
            relocate("me.saiintbrisson", "com.github.srminister.stores.misc.command")
        }
    }
}

