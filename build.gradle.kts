buildscript {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.kotlin.gradlePlugin)
        classpath(libs.kotlin.serialization)
        classpath(libs.kmp.native.coroutines)
        classpath(libs.sqldelight.gradlePlugin)
        classpath(libs.google.services.gradlePlugin)
        classpath(libs.google.firebase.crashlytics.gradlePlugin)
        classpath(libs.android.gradlePlugin)
        classpath("com.jraska.module.graph.assertion:plugin:2.5.0-LOCAL")
    }
}

allprojects {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}