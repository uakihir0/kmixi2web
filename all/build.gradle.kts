import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.swiftpackage)
    id("module.publications")
}

kotlin {
    jvmToolchain(11)
    jvm()

    js {
        nodejs()
        browser()
        binaries.library()
        compilerOptions {
            generateTypeScriptDefinitions()
            freeCompilerArgs.add("-Xes-long-as-bigint")
        }

        compilations.all {
            compileTaskProvider.configure {
                compilerOptions.moduleName.set("kmixi2web-js")
            }
        }
    }

    val xcf = XCFramework("kmixi2web")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        macosArm64(),
    ).forEach {
        it.binaries.framework {
            export(project(":core"))
            baseName = "kmixi2web"
            xcf.add(this)
        }
    }

    cocoapods {
        name = "kmixi2web"
        version = "0.1.0"
        summary = "mixi2 web client library for Kotlin Multiplatform."
        homepage = "https://github.com/uakihir0/kmixi2web"
        authors = "Akihiro Urushihara"
        license = "MIT"
        framework { baseName = "kmixi2web" }
    }

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.js.ExperimentalJsExport")
            }
        }
        commonMain.dependencies {
            api(project(":core"))
        }
    }
}

multiplatformSwiftPackage {
    swiftToolsVersion("5.7")
    targetPlatforms {
        iOS { v("15") }
        macOS { v("12.0") }
    }
}

tasks.configureEach {
    if (name.contains("assembleKmixi2web") && name.contains("XCFramework")) {
        mustRunAfter(tasks.matching { it.name.contains("FatFramework") })
    }
}

tasks.podPublishXCFramework {
    doLast {
        providers.exec {
            executable = "sh"
            args = listOf(project.projectDir.path + "/../tool/rename_podfile.sh")
        }.standardOutput.asText.get()
    }
}
