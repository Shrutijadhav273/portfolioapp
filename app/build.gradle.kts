plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.portfolioapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.portfolioapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))

    // Compose UI
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // Activity
    implementation("androidx.activity:activity-compose:1.8.2")

    // Firebase Auth
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation(platform("com.google.firebase:firebase-bom:34.11.0"))
    implementation("com.google.firebase:firebase-analytics")

    // Debug
    debugImplementation("androidx.compose.ui:ui-tooling")
}