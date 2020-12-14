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
    buildFeatures {
        viewBinding = true
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
    kotlinOptions {
        // for inlining bytecode for some methods
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

repositories {
    google()
    jcenter()
}

dependencies {
    // core module for android application
    implementation(project(":application:android:core"))

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

    // RxJava 3
    // https://github.com/ReactiveX/RxJava/
    val rxjava = "3.0.0"
    implementation("io.reactivex.rxjava3:rxjava:$rxjava")

    // RxAndroid
    // https://github.com/ReactiveX/RxAndroid
    val rxandroid = "3.0.0"
    implementation("io.reactivex.rxjava3:rxandroid:$rxandroid")

    // Paging
    // https://developer.android.com/jetpack/androidx/releases/paging
    val paging = properties["version.androidx.paging"]
    implementation("androidx.paging:paging-runtime:$paging")

    // Architecture components
    // https://developer.android.com/topic/libraries/architecture
    val arch = properties["version.androidx.arch"]
    implementation("android.arch.lifecycle:extensions:$arch")
    implementation("android.arch.lifecycle:viewmodel:$arch")

    // SpannedGridLayoutManager for the RecyclerView
    // https://github.com/Arasthel/SpannedGridLayoutManager
    val spannedGridLayoutManager = "3.0.2"
    implementation("com.arasthel:spannedgridlayoutmanager:$spannedGridLayoutManager")

    // Shimmer effect
    // https://github.com/facebook/shimmer-android
    val shimmer = properties["version.facebook.shimmer"]
    implementation("com.facebook.shimmer:shimmer:$shimmer")

    // Sliding Up Panel Layout
    // https://github.com/umano/AndroidSlidingUpPanel
    val slidinguppanel = properties["version.slidinguppanel"]
    implementation("com.sothree.slidinguppanel:library:$slidinguppanel")

    // Extensions for Fragments (e.g. FragmentContainerView)
    // https://medium.com/@pavan.careers5208/fragmentcontainerview-c39d8ac376d1
    val fragment = properties["version.androidx.fragment"]
    implementation("androidx.fragment:fragment-ktx:$fragment")

    // Android GifDrawable
    // https://github.com/koral--/android-gif-drawable
    val gif = properties["version.gifdrawable"]
    implementation("pl.droidsonroids.gif:android-gif-drawable:$gif")

    // ExoPlayer
    // https://github.com/google/ExoPlayer
    val exoplayer = properties["version.exoplayer"]
    implementation("com.google.android.exoplayer:exoplayer:$exoplayer")

    // Dexter (request permissions)
    // https://github.com/Karumi/Dexter/
    val dexter = properties["version.dexter"]
    implementation("com.karumi:dexter:$dexter")

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
