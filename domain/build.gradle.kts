plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Dependencies.ProjectConstants.COMPILE_SDK

    defaultConfig {
        minSdk = Dependencies.ProjectConstants.MINIMUM_SDK
        targetSdk = Dependencies.ProjectConstants.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        named("debug") {
        }

        named("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
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
}

dependencies {

    implementation(Dependencies.Gradle.KOTLIN_STDLIB)

    testImplementation(Dependencies.Kotlin.COROUTINE_TEST)
    implementation(Dependencies.Kotlin.COROUTINE_ANDROID)
    implementation(Dependencies.Kotlin.COROUTINES_PLAY_SERVICE)

    implementation(Dependencies.Util.GOOGLE_GSON)

    implementation(Dependencies.Hilt.HILT_WORKER)
    implementation(Dependencies.Hilt.HILT_ANDROID)
    kapt(Dependencies.Hilt.HILT_ANDROID_COMPILER)
    kapt(Dependencies.Hilt.HILT_COMPILER)
    testImplementation(Dependencies.Hilt.HILT_TEST)
    androidTestImplementation(Dependencies.Hilt.HILT_TEST)
    kaptAndroidTest(Dependencies.Hilt.HILT_ANDROID_COMPILER)

    implementation(Dependencies.Firebase.STORAGE)

    testImplementation(Dependencies.Test.TRUTHY)
    testImplementation(Dependencies.Test.JUNIT)
    testImplementation(Dependencies.Test.MOCKK)
    testImplementation(Dependencies.Test.ROBOELECTRIC)
    androidTestImplementation(Dependencies.Test.TRUTHY)
    androidTestImplementation(Dependencies.Test.JUNIT_EXT)
    androidTestImplementation(Dependencies.Test.ESPRESSO)
    androidTestImplementation(Dependencies.Test.CORE_TESTING)

    implementation(Dependencies.AndroidX.PAGING)
    implementation(Dependencies.AndroidX.ROOM_PAGING)

    implementation(Dependencies.Room.RUNTIME)
    implementation(Dependencies.Room.KTX)
    kapt(Dependencies.Room.COMPILER)

    implementation(Dependencies.Network.OKHTTP)

    implementation(Dependencies.Util.TIMBER)
}