plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation(project(":annotations"))

    implementation(libs.javapoet)
    compileOnly(libs.auto.service.annotations)
    kapt(libs.auto.service)
}