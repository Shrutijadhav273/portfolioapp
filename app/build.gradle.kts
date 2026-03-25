plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.portfolioapp128"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.portfolioapp128"
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

    // ✅ FIX 1: Add Java compatibility (THIS WAS MISSING)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // ✅ FIX 2: Keep Kotlin same as Java
    kotlinOptions {
        jvmTarget = "17"
    }
}

// ✅ FIX 3: Enforce JVM toolchain (extra safe)
kotlin {
    jvmToolchain(17)
}

dependencies {

    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))

    // Compose UI
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material-icons-extended")

    // Activity
    implementation("androidx.activity:activity-compose:1.8.2")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-database-ktx")

    // Debug
    debugImplementation("androidx.compose.ui:ui-tooling")
}