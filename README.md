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
    implementation("io.github.bodenberg:appdimens-sdps:3.0.0")
}
```

### Quick Example in Compose:
```kotlin
Box(
    modifier = Modifier
        .width(100.wdp) // Scales by width
        .height(100.hdp) // Scales by height
        .padding(16.sdp) // Scales based on Smallest Width
)
```

### Advanced Conditional Example:
```kotlin
val dynamicPadding = 16.dp.scaledDp()
    .screen(UiModeType.TELEVISION, customValue = 32.sdp)
    .sdp // Result: 32.sdp on TV, 16.sdp on others
```

![Extra demonstration](IMAGES/image.png)

---
*Created with the best responsive layout practices for the Android ecosystem.*
