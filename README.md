# AppDimens SDP, HDP, WDP

![AppDimens Banner](IMAGES/banner_top.png)

Welcome to the official documentation of the **AppDimens** library. 

## 📖 What is the library?

**AppDimens SDP, HDP, WDP** is a modern dimension management system for Android. It expands the classic SDP (Scaled Density Pixels) standard by also introducing scaling by Height (HDP) and Width (WDP). The library automates the process of adjusting Dp, Sp, and Px, ensuring the layout remains perfectly scaled and responsive on any device format in a mathematically precise way.

## ⚙️ What does it do?

It provides thousands of pre-calculated `@dimen` resources (from `-300` to `600`) ready to use, saving the developer the work of calculating sizes for every Android screen variant.

* **SDP (Smallest Width DP):** Scales the dimension based on the smallest width available on the device. Perfect for maintaining the screen proportion in most scenarios (e.g., `@dimen/_16sdp` or `16.sdp`).
* **WDP (Width DP):** Scales specifically based on the exact horizontal width of the device in the current orientation (e.g., `@dimen/_16wdp` or `16.wdp`).
* **HDP (Height DP):** Scales specifically based on the exact vertical height of the device (e.g., `@dimen/_16hdp` or `16.hdp`).
* **Dynamic Conditionals (Compose):** Makes it easy to adapt the dimension based on the device type (Car, TV, Watch) using the `.scaledDp()` instruction.

<br/>
<p align="center">
  <img src="IMAGES/screenshot.png" alt="Layout example" width="25%" />
</p>
<br/>

### 📏 Physical Units (DimenPhysicalUnits)

Besides relative screen scaling, the library provides direct support for converting real physical measurement units (Millimeters, Centimeters, and Inches) for layout use, ensuring absolute size regardless of device density.

* **Native Compose Extensions:** Use `.mm`, `.cm`, and `.inch` directly on `Float` or `Int` to get the equivalent value in `Dp` or `Px`.
  * *Example:* `10.mm`, `2.5f.cm`, `1.inch`.
* **Radius and Diameter Utilities:** Convenient functions like `.radius()` make drawing circular components with physical measurements easy.
* **Use in Legacy Code (XML/Java):** The utility class `DimenPhysicalUnits` provides accessible methods (e.g., `toDpFromMm`, `toPxFromCm`) that require the current `Resources` to perform vital conversions based on the device's DPI (Android standard).

## ✨ What's New in Version 3.0.0

* **Code Extensibility (Java & Kotlin):** Full support for AppDimens outside of XML and Compose! You can now resolve dimensions dynamically in your backend UI logic using the `DimenSdp` class (e.g., `DimenSdp.sdp(context, 16)`).
* **Optional Orientation:** The `orientation` parameter is now optional in conditional `screen()` functions, defaulting to `Orientation.DEFAULT`.
* **Dynamic Inverters:** Added scaling logic that can automatically swap `HEIGHT` and `WIDTH` when the screen rotates to inverted positions (e.g., using `hdp_lw`, `wdp_lh`, or the `Inverter` enum) ensuring precise responsive layouts on orientation changes!

## 🚀 Advantages

1. **Accelerated Development:** Eliminates the need to create massive manual `dimens.xml` files for various screen categories (like `values-sw320dp`, `values-sw600dp`, etc.). Everything comes unified.
2. **Direct Hybrid Integration:** Works incredibly well in both traditional **XML** (`View System`) and the modern **Jetpack Compose** era.
3. **Cleaner Layouts:** With the fluent Compose architecture (e.g., `16.sdp`), margins become readable and concise.
4. **Precision for TV, Wear OS, and Auto:** Handles advanced rules without complexity using `UiModeType` combined with qualifiers.

## ⚡ Performance

The implementation ensures zero or virtually zero performance impact:
* **In XML:** All tags like `@dimen/_16sdp` are processed statically at build time and resolved natively in parallel with Android Framework resources.
* **In Compose:** Accessing `.sdp`, `.hdp`, and `.wdp` uses optimized functions that extract dimensions via native context caching (`LocalConfiguration` and injected IDs). Avoiding unnecessary processing, it respects conventional UI steps without forcing useless recompositions.

## 🛠️ Support and Installation

The library has broad support in the Android ecosystem and is updated for the most recently launched paradigms.

* **Min SDK:** 24
* **Compile SDK:** 36
* **Languages:** Kotlin and Java.
* **Paradigm:** XML and Jetpack Compose.

To install, simply add it to your `build.gradle` (dependency):

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-sdps:3.0.2")
}
```

## 💻 Usage Examples

### 1. Jetpack Compose

**Basic Usage (Auto-Scaling Layouts):**
The simplest way to use AppDimens. Values automatically scale based on the device's characteristics.
```kotlin
Box(
    modifier = Modifier
        // .wdp scales relative to the device's width
        .width(100.wdp) 
        // .hdp scales relative to the device's height
        .height(100.hdp) 
        // .sdp scales relative to the smallest width (like standard sdps)
        .padding(16.sdp) 
) {
    Text("Hello World", fontSize = 14.sdp.value.sp) // You can also scale Text Sp like this
}
```

**Advanced Conditional Scaling (UiModeType & Orientation):**
Sometimes you need completely different sizes or rules for specific form factors. You can chain `.screen()` conditions starting from a base dimension using `.scaledDp()`.
```kotlin
val dynamicPadding = 16.scaledDp() // 16.sdp is the default 
    // 1. If running on a TV, use 32
    .screen(
        type = UiModeType.TELEVISION, 
        customValue = 32 
    )
    // 2. If running on a Watch, use 8
    .screen(
        type = UiModeType.WATCH, 
        customValue = 8
    )
    // 3. If in Landscape orientation, use 20
    .screen(
        orientation = Orientation.LANDSCAPE, 
        customValue = 20
    )
    .sdp // Finally, resolve the value!
```

**Dynamic Inverters (Handling Screen Rotation):**
If you have a component that is 600.wdp (very wide), rotating the device to portrait will probably break your layout because the width is now much smaller. You can use the `inverter` feature to automatically swap your WDP/HDP metrics upon rotation!
```kotlin
val adaptiveBoxWidth = 300.scaledDp()
    .screen(
        // When device is > 600 Width (tablets) in Landscape...
        type = DpQualifier.WIDTH, 
        value = 600, 
        orientation = Orientation.LANDSCAPE, 
        
        // ...use 400.wdp...
        customValue = 400.sdp,
        
        // ...but if we rotate to PORTRAIT, this 400 'Width' 
        // automatically acts as 400 'Height', preserving proportions!
        inverter = Inverter.PW_TO_LH 
    )
    .wdp
```

### 2. XML Layouts

You can use the exact same dimensions straight in your XML layout files!

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="@dimen/_16sdp">

    <!-- Scales seamlessly based on smallest width -->
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="@dimen/_14sdp" 
        android:layout_marginBottom="@dimen/_8wdp" />
        
    <!-- Scales based on explicit height -->
    <View
        android:layout_width="@dimen/_50wdp"
        android:layout_height="@dimen/_50hdp" />
</LinearLayout>
```

### 3. Java & Kotlin (Code Level)

Need to calculate layout parameters programmatically outside of Compose or XML? Use the new `com.appdimens.sdps.code.DimenSdp` utility class!

**Kotlin: Direct Value Resolution**
When writing custom ViewGroups, View extensions, or WindowInsets:
```kotlin
// Get dimension in exact pixels directly
val paddingPx: Float = DimenSdp.sdp(context, 16)
val widthPx: Float = DimenSdp.wdp(context, 100)

// Quick Inverters in Code!
// Gets 50.hdp (Height), but if the device is rotated to Landscape, 
// it extracts 50.wdp (Width) automatically.
val adaptivePx: Float = DimenSdp.hdp_lw(context, 50) 
```

**Kotlin: Advanced Scalable Values in Code**
You have access to the exact same `.screen()` scaling API in pure Kotlin as in Compose!
```kotlin
val finalDynamicPx: Float = DimenSdp.scaled(16) // Default is 16
    // If running on Android TV, use 32px
    .screen(type = UiModeType.TELEVISION, customValue = 32)
    
    // If smallest-width is > 600 (tablets), use 24px
    .screen(type = DpQualifier.SMALL_WIDTH, value = 600, customValue = 24)
    
    // Resolve everything relative to Smallest Width (sdp) and convert to pixels
    .sdp(context) 
```

**Java: Equivalents**
The Java API uses traditional method overloads `@JvmOverloads` and `@JvmStatic`:
```java
// 1. Direct Resolution
float paddingPx = DimenSdp.sdp(context, 16);
float adaptivePx = DimenSdp.hdp_lw(context, 50);

// 2. Resolve the raw resource ID instead of the pixel float
int dimenResId = DimenSdp.sdpRes(context, 16);
view.setPadding(context.getResources().getDimensionPixelSize(dimenResId), ...);

// 3. Conditional Builder
float dynamicPx = DimenSdp.scaled(16)
    // Java requires positional arguments since named arguments don't exist
    .screen(UiModeType.TELEVISION, 32) // uiModeType, customValue
    // type, value, customValue, orientation, inverter
    .screen(DpQualifier.SMALL_WIDTH, 600, 24, null, Orientation.DEFAULT, Inverter.DEFAULT)
    .sdp(context);
```

![Extra demonstration](IMAGES/image.png)

---
*Created with the best responsive layout practices for the Android ecosystem.*
