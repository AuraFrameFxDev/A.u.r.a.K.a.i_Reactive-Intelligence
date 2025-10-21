import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    `maven-publish`
    `java-library`
    kotlin("jvm")
}

version = "1.0.0"

java {
    toolchain { languageVersion = JavaLanguageVersion.of(24) }
}

kotlin {
    jvmToolchain(24)
    }


dependencies {
    // Pure Kotlin JVM module - no Android dependencies
    implementation(kotlin("stdlib"))
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.coroutines)

    testImplementation(libs.junit4)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.mockk)
}

tasks.test {
    useJUnitPlatform()
}