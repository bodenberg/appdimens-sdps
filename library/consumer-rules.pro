# ============================================================================
# AppDimens SDP / HDP / WDP — consumer ProGuard / R8 rules
# Merged into every app that depends on this library when minifyEnabled is
# true (R8 default AND full mode).
#
# Strategy: keep only the public API contract. Everything else (private and
# internal members, unused classes) is fully shrinkable/optimizable by the
# consuming app's R8 full mode -> maximum performance with full compatibility.
# ============================================================================

# --- Public API surface -------------------------------------------------------
# All public types of the library keep their public/protected members
# (constructors, @JvmStatic accessors, extension properties, composables,
# data-class getters, object INSTANCE fields, ...). R8 may still remove
# private/internal code and optimize method bodies.
-keep class com.appdimens.sdps.** { public protected *; }

# --- Enums (Java interop) -----------------------------------------------------
# Enum.valueOf / values / constant fields must survive for Java callers.
-keepclassmembers enum com.appdimens.sdps.** {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# --- Dynamic dimen lookup -----------------------------------------------------
# Dimen resources are resolved by name at runtime (Resources.getIdentifier);
# resource shrinking is handled by res/raw/keep.xml (tools:keep) packaged in
# this AAR, which preserves @dimen/_*sdp, @dimen/_*hdp and @dimen/_*wdp in
# consuming apps with shrinkResources enabled.