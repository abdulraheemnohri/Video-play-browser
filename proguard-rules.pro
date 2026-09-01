# Android ProGuard rules for release builds
# Add project-specific ProGuard rules here.
# By default, the Android Gradle plugin already includes ProGuard rules for:
# - All classes in the Android SDK
# - All public classes in your app's dependencies

# Keep names of classes that implement Parcelable
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep R classes
-keep class **.R$* {
    <fields>;
}

# Keep data classes (Kotlin)
-keep class * implements kotlinx.serialization.KSerializer

# Keep GeckoView classes
-keep class org.mozilla.geckoview.** { *; }

# Keep Compose classes
-keep class androidx.compose.** { *; }

# Keep Room Database classes
-keep class androidx.room.** { *; }
-keep class * extends androidx.room.Database { *; }
-keep class * extends androidx.room.Entity { *; }
-keep class * extends androidx.room.Dao { *; }

# Keep DataStore classes
-keep class androidx.datastore.** { *; }

# Keep Kotlin coroutines
-keep class kotlinx.coroutines.** { *; }

# Keep Retrofit and OkHttp if used
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }

# Keep GSON if used
-keep class com.google.gson.** { *; }

# Keep all activities, services, and receivers
-keep class * extends android.app.Activity
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver

# Keep all ViewModels
-keep class * extends androidx.lifecycle.ViewModel

# Keep all Composable functions
-keep class *Kt {
    <methods>;
}
