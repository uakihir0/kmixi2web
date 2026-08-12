import java.util.Locale

pluginManagement {
    includeBuild("plugins")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "kmixi2web"

include("core")

val osName = System.getProperty("os.name").lowercase(Locale.getDefault())
if (!osName.contains("windows")) {
    include("all")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
