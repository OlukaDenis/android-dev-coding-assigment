plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.ensibuuko.android_dev_coding_assigment"
    compileSdk = Dependencies.ProjectConstants.COMPILE_SDK

    defaultConfig {
        minSdk = Dependencies.ProjectConstants.MINIMUM_SDK
        targetSdk = Dependencies.ProjectConstants.TARGET_SDK
        applicationId = Dependencies.ProjectConstants.BASE
        versionCode = Dependencies.ProjectConstants.VERSION_CODE
        versionName = Dependencies.ProjectConstants.VERSION_NAME

        testInstrumentationRunner = Dependencies.ProjectConstants.TEST_INSTRUMENTATION_RUNNER
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(Dependencies.AndroidX.CORE_KTX)
    implementation(Dependencies.AndroidX.APP_COMPAT)
    implementation(Dependencies.AndroidX.MATERIAL)
    implementation(Dependencies.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Dependencies.AndroidX.FRAGMENT_KTX)
    implementation(Dependencies.AndroidX.ACTIVITY_KTX)
    implementation(Dependencies.AndroidX.LIVEDATA_KTX)
    implementation(Dependencies.AndroidX.VIEWMODEL_KTX)
    implementation(Dependencies.AndroidX.PAGING)
    implementation(Dependencies.AndroidX.ROOM_PAGING)
    implementation(Dependencies.AndroidX.ANNOTATION)
    implementation(Dependencies.AndroidX.LEGACY_SUPPPORT)
    implementation(Dependencies.AndroidX.WORK_MANAGER_KTX)
    implementation(Dependencies.AndroidX.SPLASHSCREEN)
    kapt(Dependencies.AndroidX.LIFECYCLE_PROCESSOR)

    implementation(Dependencies.Navigation.NAV_UI)
    implementation(Dependencies.Navigation.NAV_FRAGMENT)
    implementation(Dependencies.Navigation.NAV_RUNTIME)

    implementation(Dependencies.Util.TIMBER)
    implementation(Dependencies.Util.COIL)
    implementation(Dependencies.Util.ALERTER)

    implementation(Dependencies.Network.GSON_CONVERTER)

    testImplementation(Dependencies.Kotlin.COROUTINE_TEST)
    implementation(Dependencies.Kotlin.COROUTINE_ANDROID)

    implementation(Dependencies.Hilt.HILT_WORKER)
    implementation(Dependencies.Hilt.HILT_ANDROID)
    kapt(Dependencies.Hilt.HILT_ANDROID_COMPILER)
    kapt(Dependencies.Hilt.HILT_COMPILER)

    testImplementation(Dependencies.Test.JUNIT)
    androidTestImplementation(Dependencies.Test.JUNIT_EXT)
    androidTestImplementation(Dependencies.Test.ESPRESSO)
}