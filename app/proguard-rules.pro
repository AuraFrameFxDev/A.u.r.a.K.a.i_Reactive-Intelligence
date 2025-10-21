# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# --- Critical Application Rules ---

# Keep the main Application class, which is the entry point.
# This is essential to prevent ClassNotFoundException at startup.
-keep public class dev.aurakai.auraframefx.AurakaiApplication { *; }

# Keep all classes that are referenced in the AndroidManifest.xml,
# such as Activities, Services, BroadcastReceivers, and ContentProviders.
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Keep Hilt and Dagger classes required for dependency injection.
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Keep Room database entities.
-keep class dev.aurakai.auraframefx.data.database.entities.** { *; }

# Keep classes annotated with @Keep. This is a good practice for classes
# accessed via reflection.
-keep @androidx.annotation.Keep class * { *; }

# --- Suppress Warnings for External Libraries ---
# These rules suppress warnings about classes that are part of the libraries'
# internal implementation and are not directly used by your app.
-dontwarn com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
-dontwarn com.google.auto.service.AutoService
-dontwarn com.google.auto.value.extension.memoized.Memoized
-dontwarn com.google.common.collect.Streams
-dontwarn jakarta.servlet.ServletContainerInitializer
-dontwarn java.lang.Module
-dontwarn java.lang.module.ModuleDescriptor
-dontwarn javax.lang.model.**
-dontwarn javax.tools.**

# --- Optimizations ---
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-dontpreverify

# --- Logging Removal ---
# Remove logging calls from release builds.
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

-assumenosideeffects class timber.log.Timber {
    public static void v(...);
    public static void i(...);
    public static void w(...);
    public static void d(...);
    public static void e(...);
}
