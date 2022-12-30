plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    //Image
    implementation("com.github.bumptech.glide:glide:4.11.0")    // Image - Glide
    implementation("io.coil-kt:coil:1.4.0")                     // Image - Coil
    implementation("io.coil-kt:coil-svg:1.3.2")                 // Image - Coil svg

    //Base
    implementation(Deps.ANDX_CORE)
    implementation(Deps.AND_MATERIAL)
    testImplementation(Deps.TEST_JUNIT)
    androidTestImplementation(Deps.AND_TEST_X_JUNIT)
}