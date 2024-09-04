import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    id("conferences4hall.multiplatform.library")
    id("conferences4hall.android.library.compose")
    id("conferences4hall.quality")
}

android {
    namespace = "org.gdglille.devfest.android.theme.m3.schedules.ui"
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
                implementation(projects.features.speakers.speakersUi)
                implementation(projects.themeM3.navigation)
                implementation(projects.style.components.markdown)
                implementation(projects.style.components.placeholder)
                implementation(projects.style.schedules)
                implementation(projects.style.speakers)
                implementation(projects.style.theme)

                implementation(compose.material3)
                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended)

                implementation(libs.jetbrains.kotlinx.collections)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(compose.uiTooling)

                api(libs.openfeedback.viewmodel)
            }
        }
    }
}
