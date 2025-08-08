plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.inno0422.myapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.inno0422.myapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

// dependencies 블록 안에 아래 내용을 추가하거나 기존 내용을 교체하세요.
dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Jetpack Compose (UI) - BOM(Bill of Materials)을 사용하여 Compose 라이브러리 버전을 관리합니다.
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.9.0")

    // Material Icons Extended (추가된 부분!) - 더 많은 아이콘을 사용하기 위해 필요해요.
    implementation("androidx.compose.material:material-icons-extended")

    // Navigation (화면 전환)
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // ViewModel (데이터 관리)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

    // 차트 라이브러리 (대시보드 그래프용)
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")


    // 테스트 관련 의존성
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
