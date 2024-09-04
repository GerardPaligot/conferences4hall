import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    id("conferences4hall.multiplatform.library")
    id("conferences4hall.android.library.compose")
    id("conferences4hall.quality")
}

android {
    namespace = "com.paligot.confily.style.events"

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
                implementation(projects.shared.resources)
                implementation(projects.themeM3.style.components.placeholder)
                implementation(projects.themeM3.style.theme)

                implementation(compose.material3)
                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended)

                api(libs.jetbrains.kotlinx.collections)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
            }
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.paligot.confily.style.events"
    generateResClass = always
}
