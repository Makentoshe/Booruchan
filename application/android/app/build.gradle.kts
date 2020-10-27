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

    // Cicerone (navigation)
    // https://github.com/terrakok/Cicerone
    val cicerone = properties["version.cicerone"]
    implementation("ru.terrakok.cicerone:cicerone:$cicerone")

    // Toothpick (dependency injection)
    // https://github.com/stephanenicolas/toothpick
    val toothpick = properties["version.toothpick"]
    implementation("com.github.stephanenicolas.toothpick:ktp:$toothpick")
    kapt("com.github.stephanenicolas.toothpick:toothpick-compiler:$toothpick")
    implementation("com.github.stephanenicolas.toothpick:smoothie:$toothpick")
    implementation("com.github.stephanenicolas.toothpick:smoothie-lifecycle-ktp:$toothpick")
    testImplementation("com.github.stephanenicolas.toothpick:toothpick-testing-junit5:$toothpick")

    // Ktor (http client)
    // https://github.com/ktorio/ktor
    val ktorVersion = properties["version.ktor"]
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")

    // Material components
    // https://github.com/material-components/material-components-android
    val material = properties["version.androidx.material"]
    implementation("com.google.android.material:material:$material")

    // Architecture components
    // https://developer.android.com/topic/libraries/architecture
    val arch = properties["version.androidx.arch"]
    implementation("android.arch.lifecycle:extensions:$arch")
    implementation("android.arch.lifecycle:viewmodel:$arch")

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

