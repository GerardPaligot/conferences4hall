import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    id("conferences4hall.multiplatform.library")
    id("conferences4hall.android.library.compose")
    id("conferences4hall.quality")
}

android {
    namespace = "com.paligot.confily.networking.ui"

    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

kotlin {
    androidTarget()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        useCommonJs()
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.shared.uiModels)
                implementation(projects.shared.resources)
                implementation(projects.themeM3.navigation)
                implementation(projects.style.theme)

                implementation(compose.material3)
                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended)

                implementation(libs.jetbrains.kotlinx.collections)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(projects.uiCamera)

                implementation(compose.preview)
            }
        }
    }
}
