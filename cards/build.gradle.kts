import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    id("maven-publish")
}

val libraryVersion = System.getenv("LIBRARY_VERSION") ?: "1.0.0-beta.1"
val libraryGroup = "com.cometchat"
val libraryArtifact = "cards-android"

android {
    namespace = "com.cometchat.cards"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    buildFeatures {
        compose = true
    }
}

publishing {
    repositories {
        maven {
            url = uri("$projectDir/distribution")
        }
    }

    publications {
        register<MavenPublication>("cards") {
            groupId = libraryGroup
            artifactId = libraryArtifact
            version = libraryVersion

            artifact("${layout.buildDirectory.get()}/outputs/aar/cards-release.aar")

            pom {
                name.set("CometChat Cards Renderer")
                description.set("Card Schema JSON renderer for Android — View + Compose")
                url.set("https://www.cometchat.com")

                licenses {
                    license {
                        name.set("CometChat License")
                        url.set("https://www.cometchat.com/terms")
                    }
                }

                withXml {
                    val dependenciesNode = asNode().appendNode("dependencies")
                    val compileDeps = project.configurations
                        .getByName("implementation")
                        .allDependencies
                        .filter { dep ->
                            dep.group != null && dep.version != null &&
                                dep.name != "unspecified" &&
                                dep.javaClass.simpleName != "DefaultSelfResolvingDependency"
                        }

                    compileDeps.forEach { dep ->
                        val depNode = dependenciesNode.appendNode("dependency")
                        depNode.appendNode("groupId", dep.group)
                        depNode.appendNode("artifactId", dep.name)
                        depNode.appendNode("version", dep.version)
                        depNode.appendNode("scope", "compile")
                    }
                }
            }
        }
    }
}

dependencies {
    // Kotlinx Serialization
    implementation(libs.kotlinx.serialization.json)

    // Coil (View + Compose)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Jetpack Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotest.property)
    testImplementation(libs.robolectric)
    testImplementation(libs.roborazzi)
}
