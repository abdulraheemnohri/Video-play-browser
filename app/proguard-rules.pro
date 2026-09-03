# GeckoView Keep Rules
-keep class org.mozilla.geckoview.** { *; }
-dontwarn org.mozilla.geckoview.**

# Room Database Keep Rules
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.**

# Ignore missing Java desktop bean references in third party libraries
-dontwarn java.beans.**
-dontwarn org.yaml.snakeyaml.**
