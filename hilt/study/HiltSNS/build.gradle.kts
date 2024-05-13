// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false

    alias(libs.plugins.kotlin.symbol.processing) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.jetbrains.kotlin.ksp) apply false
    alias(libs.plugins.jetbrains.kotlin.plugin.parcelize) apply false
    alias(libs.plugins.dynamic.feature) apply false
}