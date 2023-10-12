plugins {
    id("conferences4hall.android.library")
    id("conferences4hall.android.library.compose")
    id("conferences4hall.quality")
}

android {
    namespace = "org.gdglille.devfest.android.theme.m3.infos.feature"
}

dependencies {
    implementation(projects.shared)
    implementation(projects.themeM3.infos.infosUi)
    implementation(projects.themeM3.navigation)
    implementation(projects.themeM3.style)

    implementation(libs.kotlinx.collections)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.tooling)
    implementation(libs.androidx.compose.lifecycle)
    implementation(libs.androidx.lifecycle.vm)
    implementation(libs.compose.richtext)

    implementation(platform(libs.google.firebase))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
}