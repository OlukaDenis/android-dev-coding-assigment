import com.google.protobuf.gradle.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.protobuf")
}

android {
    compileSdk = Dependencies.ProjectConstants.COMPILE_SDK

    defaultConfig {
        minSdk = Dependencies.ProjectConstants.MINIMUM_SDK
        targetSdk = Dependencies.ProjectConstants.TARGET_SDK

        testInstrumentationRunner = "com.data.HiltTestRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        named("debug") {
            buildConfigField("String", "BASE_URL", "\"${Versions.BASE_URL}\"")
        }

        named("release") {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"${Versions.BASE_URL}\"")

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

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    sourceSets {
        this.getByName("androidTest") {
            this.assets.srcDir("src/debug/assets")
        }
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(Dependencies.Gradle.KOTLIN_STDLIB)

    implementation(Dependencies.AndroidX.DATA_STORE)
    implementation(Dependencies.AndroidX.DATA_STORE_PREFERENCES)
    implementation(Dependencies.ProtoBuf.JAVA_LITE)
    implementation(Dependencies.ProtoBuf.KOTLIN_LITE)

    implementation(Dependencies.Firebase.DYNAMIC_LINKS)

    implementation(Dependencies.Network.OKHTTP)
    implementation(Dependencies.Network.RETROFIT)
    implementation(Dependencies.Network.GSON_CONVERTER)
    implementation(Dependencies.Network.LOGGING_INTERCEPTOR)
    testImplementation(Dependencies.Network.MOCK_WEB_SERVER)
    androidTestImplementation(Dependencies.Network.MOCK_WEB_SERVER)

    testImplementation(Dependencies.Kotlin.COROUTINE_TEST)
    implementation(Dependencies.Kotlin.COROUTINE_ANDROID)

    implementation(Dependencies.Hilt.HILT_WORKER)
    implementation(Dependencies.Hilt.HILT_ANDROID)
    kapt(Dependencies.Hilt.HILT_ANDROID_COMPILER)
    kapt(Dependencies.Hilt.HILT_COMPILER)
    testImplementation(Dependencies.Hilt.HILT_TEST)
    androidTestImplementation(Dependencies.Hilt.HILT_TEST)
    kaptAndroidTest(Dependencies.Hilt.HILT_ANDROID_COMPILER)

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
    testImplementation(Dependencies.Room.TEST)

    implementation(Dependencies.Util.TIMBER)

    implementation(project(":domain"))
}

protobuf {
    protoc {
        artifact = Dependencies.ProtoBuf.ARTIFACT
    }

    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("java") {
                    option("lite")
                }
                create("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

fun getProperty(key: String): String {
    return try {
        val properties = org.jetbrains.kotlin.konan.properties.loadProperties("local.properties")
        properties.getProperty(key)
    } catch (exception: Exception) {
        getSystemProperty(key)
    }
}

fun getSystemProperty(key: String): String = System.getenv(key)