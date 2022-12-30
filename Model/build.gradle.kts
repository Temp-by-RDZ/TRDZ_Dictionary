plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = 32

    buildFeatures{
        viewBinding = true
    }

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(project(":Utility"))
    api(project(":Model:data"))
    api(project(":Model:SourceServer"))
    api(project(":Model:SourceRoom"))

    //Coroutines
    implementation(Deps.COROUTINES_CORE)
    implementation(Deps.COROUTINES_ANDROID)
    //Base
    implementation(Deps.ANDX_CORE)
    testImplementation(Deps.TEST_JUNIT)
    androidTestImplementation(Deps.AND_TEST_X_JUNIT)

}