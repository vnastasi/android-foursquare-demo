plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
    id("org.jetbrains.kotlin.plugin.allopen") version "1.3.31"
}

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "nl.zoostation.fsd"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "nl.zoostation.fsd.runner.EspressoTestRunner"

        buildConfigField("String", "BASE_URL", "\"https://api.foursquare.com\"")
        buildConfigField("String", "CLIENT_ID", "\"L2UXPM54VOBZJRQWKVIZLYAQHJJH3FTJTQIBULVQO2E0FQ2L\"")
        buildConfigField("String", "CLIENT_SECRET", "\"FEQ4JVCEMTJSWLL5ZMWYOZB1SO2C241NP0WRPFXYWAHWIRHL\"")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    dataBinding {
        isEnabled = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

androidExtensions {
    isExperimental = true
}

allOpen {
    annotation("nl.zoostation.fsd.app.Open")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.41")
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.recyclerview:recyclerview:1.0.0")
    implementation("androidx.arch.core:core-runtime:2.0.1")
    implementation("androidx.lifecycle:lifecycle-livedata:2.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.0.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.0.0")
    implementation("androidx.core:core-ktx:1.0.2")
    implementation("androidx.fragment:fragment-ktx:1.0.0")
    implementation("androidx.annotation:annotation:1.1.0")
    implementation("com.google.android.material:material:1.1.0-alpha08")
    implementation("androidx.room:room-runtime:2.1.0")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("io.reactivex.rxjava2:rxjava:2.2.8")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("io.reactivex.rxjava2:rxkotlin:2.3.0")
    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.9.1")
    implementation("com.google.dagger:dagger:2.16")
    implementation("com.github.bumptech.glide:glide:4.9.0")

    kapt("androidx.room:room-compiler:2.1.0")
    kapt("com.google.dagger:dagger-compiler:2.16")
    kapt("com.github.bumptech.glide:compiler:4.9.0")

    testImplementation("junit:junit:4.12")
    testImplementation("org.assertj:assertj-core:3.12.2")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
    testImplementation("androidx.room:room-testing:2.1.0")
    testImplementation("androidx.arch.core:core-testing:2.0.1")

    androidTestImplementation("androidx.test:core:1.2.0")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test:rules:1.2.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.2.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.0.1")
    androidTestImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0") {
        exclude(group = "org.mockito", module = "mockito-core")
    }
    androidTestImplementation("org.mockito:mockito-android:2.27.0")
}
