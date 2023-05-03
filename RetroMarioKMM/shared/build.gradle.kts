plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.8.10"
}

kotlin {
    android()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {

                api("org.jetbrains.kotlin:kotlin-stdlib-common")

                implementation ("org.jetbrains.kotlin:kotlin-stdlib-common:1.8.10")
                // FIRE STORE
                implementation("dev.gitlive:firebase-firestore:1.6.2")
                implementation("dev.gitlive:firebase-auth:1.6.2")

                // SERIALIZATION
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

                //COROUTINE
                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting{
            dependencies {
                // FIREBASE
                implementation("com.google.firebase:firebase-core:21.1.1")
                implementation("com.google.firebase:firebase-firestore:24.4.1")

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.example.retromariokmm"
    compileSdk = 33
    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
}
dependencies {
    implementation("com.google.firebase:firebase-auth-ktx:21.1.0")
}
