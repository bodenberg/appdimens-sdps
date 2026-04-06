# AppDimens SDP / HDP / WDP — consumer ProGuard / R8 rules
# Merged into apps that depend on this library when minifyEnabled is true.
# Covers R8 default and full mode (android.enableR8.fullMode=true).

# --- Enums in the public API (Java Enum.valueOf / values) ---
-keepclassmembers enum com.appdimens.sdps.common.** {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# --- Kotlin objects exposed to Java via @JvmStatic ---
-keep class com.appdimens.sdps.code.DimenSdp { *; }
-keep class com.appdimens.sdps.code.DimenSsp { *; }
-keep class com.appdimens.sdps.code.DimenPhysicalUnits { *; }

# --- DimenScaled / ScaledSp builders (code + Compose packages) ---
-keep class com.appdimens.sdps.code.DimenScaled { *; }
-keep class com.appdimens.sdps.compose.DimenScaled { *; }
-keep class com.appdimens.sdps.code.ScaledSp { *; }
-keep class com.appdimens.sdps.compose.ScaledSp { *; }

# --- Data classes used by the builder API ---
-keep class com.appdimens.sdps.common.DpQualifierEntry { *; }
-keep class com.appdimens.sdps.code.CustomDpEntry { *; }
-keep class com.appdimens.sdps.compose.CustomDpEntry { *; }
-keep class com.appdimens.sdps.code.CustomSpEntry { *; }
-keep class com.appdimens.sdps.compose.CustomSpEntry { *; }

# Dynamic dimen lookup uses Resources.getIdentifier; resource shrinking is handled via
# res/raw/keep.xml (tools:keep) packaged in this AAR.
