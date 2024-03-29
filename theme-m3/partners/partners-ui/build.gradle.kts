plugins {
    id("conferences4hall.android.library")
    id("conferences4hall.android.library.compose")
    id("conferences4hall.quality")
}

android {
    namespace = "org.gdglille.devfest.android.theme.m3.partners.ui"
}

dependencies {
    implementation(projects.shared.uiModels)
    implementation(projects.themeM3.navigation)
    implementation(projects.themeM3.style.components.markdown)
    implementation(projects.themeM3.style.components.placeholder)
    implementation(projects.themeM3.style.events)
    implementation(projects.themeM3.style.partners)
    implementation(projects.themeM3.style.theme)

    implementation(libs.jetbrains.kotlinx.collections)

    implementation(compose.material3)
    implementation(compose.preview)
    debugImplementation(compose.uiTooling)
    implementation(compose.materialIconsExtended)

    implementation(libs.coil.compose)
}
