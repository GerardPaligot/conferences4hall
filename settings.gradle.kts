pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.google.cloud.tools.appengine")) {
                useModule("com.google.cloud.tools:appengine-gradle-plugin:${requested.version}")
            }
        }
    }
}

plugins {
    // This plugin is used because we can't configure a jvm toolchain version that isn't installed
    // on the local machine without this plugin. e.g. JVM 21 installed but JVM toolchain 17
    // configured in the project.
    // https://youtrack.jetbrains.com/issue/KTIJ-24981/Gradle-8.-project-sync-fails-with-an-error-No-matching-toolchains-found-for-requested-specification-if-there-is-no-necessary-JDK
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

rootProject.name = "confily"
include(":androidApp")
include(":android-core:core-models-factory")
include(":android-core:core-sample")
include(":android-core:core-test")
include(":android-core:core-test-patterns")
include(":features:event-list:event-list-ui")
include(":features:event-list:event-list-presentation")
include(":features:event-list:event-list-panes")
include(":features:event-list:event-list-di")
include(":features:infos:infos-ui")
include(":features:infos:infos-presentation")
include(":features:infos:infos-panes")
include(":features:infos:infos-di")
include(":features:main:main")
include(":features:main:main-di")
include(":features:navigation")
include(":features:networking:networking-ui")
include(":features:networking:networking-panes")
include(":features:networking:networking-presentation")
include(":features:networking:networking-di")
include(":features:partners:partners-ui")
include(":features:partners:partners-panes")
include(":features:partners:partners-presentation")
include(":features:partners:partners-semantics")
include(":features:partners:partners-di")
include(":features:schedules:schedules-ui")
include(":features:schedules:schedules-sample")
include(":features:schedules:schedules-panes")
include(":features:schedules:schedules-presentation")
include(":features:schedules:schedules-di")
include(":features:schedules:schedules-test")
include(":features:schedules:schedules-test-scopes")
include(":features:schedules:schedules-semantics")
include(":features:speakers:speakers-ui")
include(":features:speakers:speakers-sample")
include(":features:speakers:speakers-panes")
include(":features:speakers:speakers-presentation")
include(":features:speakers:speakers-semantics")
include(":features:speakers:speakers-di")
include(":features:speakers:speakers-test")
include(":features:speakers:speakers-test-scopes")
include(":shared:core")
include(":shared:core-api")
include(":shared:core-db")
include(":shared:core-di")
include(":shared:core-fs")
include(":shared:models")
include(":shared:resources")
include(":shared:ui-models")
include(":backend")
include(":style:components:adaptive")
include(":style:components:markdown")
include(":style:components:permissions")
include(":style:components:placeholder")
include(":style:events")
include(":style:networking")
include(":style:partners")
include(":style:schedules")
include(":style:speakers")
include(":style:theme")
include(":ui-camera")
include(":webApps:speakerApp")
include(":wear:wear-app")
include(":wear:wear-features:events:events-di")
include(":wear:wear-features:events:events-panes")
include(":wear:wear-features:events:events-presentation")
include(":wear:wear-features:schedules:schedules-di")
include(":wear:wear-features:schedules:schedules-panes")
include(":wear:wear-features:schedules:schedules-presentation")
include(":wear:wear-features:schedules:schedules-ui")
include(":wear:wear-resources")
include(":wear:wear-theme")
include(":widgets:widgets-presentation")
include(":widgets:widgets-panes")
include(":widgets:widgets-style")
include(":widgets:widgets-ui")
include(":baselineprofile")
