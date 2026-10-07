import org.gradle.api.tasks.compile.JavaCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    id("module.publications")
}

kotlin {
    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    js {
        nodejs()
        browser()
        compilerOptions {
            freeCompilerArgs.add("-Xes-long-as-bigint")
        }
    }

    if (HostManager.hostIsMac) {
        iosX64()
        iosArm64()
        iosSimulatorArm64()
        macosArm64()
    }

    compilerOptions {
        freeCompilerArgs.addAll(
            "-XXLanguage:+JsAllowExportingSuspendFunctions",
            "-Xexpect-actual-classes",
        )
    }

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.js.ExperimentalJsExport")
                optIn("kotlinx.serialization.ExperimentalSerializationApi")
            }
        }

        commonMain.dependencies {
            implementation(libs.ktor.core)
            implementation(libs.coroutines.core)
            api(libs.serialization.protobuf)
        }

        jvmMain.dependencies {
            implementation(libs.ktor.okhttp)
        }

        jsMain.dependencies {
            implementation(libs.ktor.js)
        }

        appleMain.dependencies {
            implementation(libs.ktor.darwin)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.coroutines.test)
        }

        jvmTest.dependencies {
            implementation(libs.slf4j.simple)
            implementation(libs.serialization.json)
        }
    }
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(11)
}
