import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("conferences4hall.multiplatform.library")
    id("conferences4hall.quality")
    id("kotlinx-serialization")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

kotlin {
    android()
    jvm()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "models"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.ktor.client.serialization)
                implementation(libs.ktor.serialization.json)
                implementation(libs.kotlinx.datetime)
            }
        }
        val androidMain by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val jvmMain by getting
    }
}
