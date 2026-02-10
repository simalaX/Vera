plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.simala"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.vera"
        minSdk = 25 // FIXED: Increased from 24 to 25 to resolve the Manifest Merger failure
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Updated to Java 17, which is the standard for modern Android/Kotlin 2026
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // 1. Google Maps
    implementation("com.google.maps.android:maps-compose:6.2.1")
    implementation("com.google.android.gms:play-services-maps:19.0.0")

    // 2. Core & Lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // 3. Compose BOM & UI
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)

    // 4. Material Design - We are using Material 3 for the modern Vera Look
    implementation(libs.androidx.compose.material3)

    // REMOVED: libs.androidx.compose.material (Material 2)
    // This was likely pulling in the Wear OS components causing your crash.

    // 5. Backend & Background Services
    implementation("com.squareup.retrofit2:retrofit:2.11.0") // Updated version
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.google.android.gms:play-services-location:21.3.0") // Updated version
    implementation("androidx.work:work-runtime-ktx:2.9.1") // Updated version
        // ... other dependencies
        implementation("androidx.compose.material:material-icons-extended")

    // 6. Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}