plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE*")
        exclude("META-INF/NOTICE*")
        exclude("META-INF/*.kotlin_module")
    }
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        // Allows to use kotlin.Result type as a return
        kotlinOptions.freeCompilerArgs = listOf("-Xallow-result-return-type")
    }
}

dependencies {
    // core module for all applications (android, ios, web, etc)
    api(project(":application:core"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(kotlin("stdlib-jdk8"))

    // Room (sql database library)
    // https://developer.android.com/topic/libraries/architecture/room
    val room = "2.2.5"
    api("androidx.room:room-runtime:$room")
    api("androidx.room:room-ktx:$room")
    kapt("androidx.room:room-compiler:$room")

    // Material components
    // https://github.com/material-components/material-components-android
    val material = properties["version.androidx.material"]
    api("com.google.android.material:material:$material")

    val core = properties["version.androidx.core"]
    implementation("androidx.core:core-ktx:$core")

    val appcompat = properties["version.androidx.appcompat"]
    implementation("androidx.appcompat:appcompat:$appcompat")

    val constraint = properties["version.androidx.constraint"]
    implementation("androidx.constraintlayout:constraintlayout:$constraint")

    testImplementation("junit:junit:4.12")

    val runner = properties["version.androidx.test.runner"]
    androidTestImplementation("androidx.test:runner:$runner")

    val espresso = properties["version.androidx.test.espresso"]
    androidTestImplementation("androidx.test.espresso:espresso-core:$espresso")
}
