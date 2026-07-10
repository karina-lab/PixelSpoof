plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "org.klab.pixelspoof"
    compileSdk = 37

    defaultConfig {
        applicationId = "org.klab.pixelspoof"
        minSdk = 29
        targetSdk = 37
        versionCode = project.property("versionCode").toString().toInt()
        versionName = project.property("versionName").toString()
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    compileOnly(libs.xposed.api)
}
