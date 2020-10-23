plugins {
    id("com.android.application")
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
        applicationId = "com.makentoshe.booruchan.application.android"
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
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":gelbooru"))
    implementation(project(":danbooru"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(kotlin("stdlib-jdk8"))

    // Cicerone
    // https://github.com/terrakok/Cicerone
    val cicerone = "5.1.1"
    implementation("ru.terrakok.cicerone:cicerone:$cicerone")

    // Toothpick
    // https://github.com/stephanenicolas/toothpick
    val toothpick = "3.1.0"
    implementation("com.github.stephanenicolas.toothpick:ktp:$toothpick")
    kapt("com.github.stephanenicolas.toothpick:toothpick-compiler:$toothpick")
    implementation("com.github.stephanenicolas.toothpick:smoothie:$toothpick")
    implementation("com.github.stephanenicolas.toothpick:smoothie-lifecycle-ktp:$toothpick")
    testImplementation("com.github.stephanenicolas.toothpick:toothpick-testing-junit5:$toothpick")

    // Material components
    // https://github.com/material-components/material-components-android
    val material = "1.3.0-alpha01"
    implementation("com.google.android.material:material:$material")

    // Architecture components
    // https://developer.android.com/topic/libraries/architecture
    val arch = "1.1.1"
    implementation("android.arch.lifecycle:extensions:$arch")
    implementation("android.arch.lifecycle:viewmodel:$arch")

    val core = "1.3.0"
    implementation("androidx.core:core-ktx:$core")

    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.2")
    testImplementation("junit:junit:4.12")

    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}

