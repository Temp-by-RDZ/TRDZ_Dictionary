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
    implementation(project(":Model:data"))

    //Server
    implementation(Deps.RETROFIT_CORE)  //Functional Requests
    implementation(Deps.RETROFIT_CONV)  //Functional Requests Converter
    implementation(Deps.RETROFIT_JSON)  //Server Json feature
    //Koin
    implementation(Deps.KOIN_CORE)
    implementation(Deps.KOIN_ANDROID)   //Koin Features (Scope,ViewModel...)
    implementation(Deps.KOIN_COMPAT)    //Koin Java Compatibility
    //Base
    implementation(Deps.ANDX_CORE)
    testImplementation(Deps.TEST_JUNIT)
    androidTestImplementation(Deps.AND_TEST_X_JUNIT)

}