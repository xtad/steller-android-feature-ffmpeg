buildscript {
    repositories {
        google()
        mavenCentral()
        maven {
            name = "reposiliteRepository"
            url = uri("https://reposilite.dashboards.steller.co/releases/")
        }
    }

    dependencies {
        classpath("com.android.tools.build:gradle:9.2.1")
    }
}

plugins {
    id("com.android.library")
    id("app.artyomd.injector")
}

android {
    namespace = "com.mombo.steller.feature.ffmpeg"
    compileSdk = ApplicationConfig.targetSdk

    defaultConfig {
        minSdk = ApplicationConfig.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        create("alpha") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        create("beta") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }
}

dependencies {
    implementation(libs.coroutinesCore)
    implementation(libs.koinCore)
    implementation(libs.timber)
    // FFMPEG compiled and wrapped for Android
    implementation("com.arthenica:ffmpeg-kit-https-gpl-openssl:6.0-5@aar")
    // We need to add this library explicitly, because R8 keeps removing it
    implementation("com.arthenica:smart-exception-java:0.2.1")
}

injectConfig {
}