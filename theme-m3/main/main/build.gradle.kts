plugins {
    id("conferences4hall.android.library")
    id("conferences4hall.android.library.compose")
    id("conferences4hall.quality")
}

android {
    namespace = "org.gdglille.devfest.android.theme.m3.main"
}

dependencies {
    api(projects.themeM3.schedules.schedulesUi)
    api(projects.themeM3.speakers.speakersUi)
    api(projects.themeM3.style.theme)
    implementation(projects.themeM3.schedules.schedulesFeature)
    implementation(projects.themeM3.speakers.speakersFeature)
    implementation(projects.themeM3.networking.networkingFeature)
    implementation(projects.themeM3.partners.partnersFeature)
    implementation(projects.themeM3.infos.infosFeature)
    implementation(projects.themeM3.eventList.eventListFeature)
    implementation(projects.themeM3.navigation)
    implementation(projects.themeM3.style.components.adaptive)
    implementation(projects.shared.core)
    implementation(projects.shared.uiModels)
    implementation(projects.shared.resources)

    implementation(libs.koin.androidx.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3.windowsizeclass)
    implementation(libs.bundles.androidx.compose.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.navigation.suite)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(compose.material3)
    implementation(compose.components.resources)
    implementation(compose.preview)
    debugImplementation(compose.uiTooling)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.jetbrains.kotlinx.collections)

    implementation(platform(libs.google.firebase.bom))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
}
