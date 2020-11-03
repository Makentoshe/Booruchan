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

        // Allows to use kotlin.Result type as a return
        kotlinOptions.freeCompilerArgs = listOf("-Xallow-result-return-type")
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":gelbooru"))
    implementation(project(":danbooru"))
    implementation(project(":application:core"))

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

    // Coroutines
    val coroutines = properties["version.coroutines"]
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines")

    // Coroutines for android ViewModel
    val coroutinesViewModel = properties["version.coroutines.viewmodel"]
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$coroutinesViewModel")

    // Paging
    // https://developer.android.com/jetpack/androidx/releases/paging
    val paging = properties["version.androidx.paging"]
    implementation("androidx.paging:paging-runtime:$paging")

    // Material components
    // https://github.com/material-components/material-components-android
    val material = properties["version.androidx.material"]
    implementation("com.google.android.material:material:$material")

    // Architecture components
    // https://developer.android.com/topic/libraries/architecture
    val arch = properties["version.androidx.arch"]
    implementation("android.arch.lifecycle:extensions:$arch")
    implementation("android.arch.lifecycle:viewmodel:$arch")

    // SpannedGridLayoutManager for the RecyclerView
    // https://github.com/Arasthel/SpannedGridLayoutManager
    val spannedGridLayoutManager = "3.0.2"
    implementation("com.arasthel:spannedgridlayoutmanager:$spannedGridLayoutManager")

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

    // Additional dependency for Jackson
    // Todo should be added as a transitive dependency for the library in the future
    implementation("javax.xml.stream:stax-api:1.0-2")
}
