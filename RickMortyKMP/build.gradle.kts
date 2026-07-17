import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.sqldelight)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "RickMortyKMP"
            isStatic = true
        }
    }

    androidLibrary {
        namespace = "com.jetbrains.rickmorty"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqldelight.android.driver)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqldelight.native.driver)
        }
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.sqldelight.coroutines)
        }
        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
            implementation(kotlin("test"))
            implementation(libs.ktor.client.mock)
            // implementation(libs.sqldelight.sqlite.driver)
        }
    }
}

sqldelight {
    databases {
        create("RickMortyDatabase") {
            packageName.set("com.jetbrains.rickmorty.db")
        }
    }
}
