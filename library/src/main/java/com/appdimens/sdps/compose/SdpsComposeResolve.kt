/**
 * Shared Compose resolve helpers: minimize CompositionLocal reads and remember stable work.
 *
 * - DEFAULT inverter + no aspect-ratio: no [androidx.compose.ui.platform.LocalConfiguration] read
 *   ( [androidx.compose.ui.res.dimensionResource] already tracks resource configuration ).
 * - Inverters: subscribe only to `LocalConfiguration.orientation`.
 * - Aspect-ratio: subscribe only to sw/w/h/dpi fields used by [com.appdimens.sdps.core.AppDimensSdpsFactors].
 */
package com.appdimens.sdps.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.appdimens.sdps.common.DpQualifier
import com.appdimens.sdps.common.Inverter
import com.appdimens.sdps.common.effectiveDpQualifier
import com.appdimens.sdps.core.AppDimensSdpsFactors
import com.appdimens.sdps.core.DimenResourceIdCache
import kotlin.math.abs

@Composable
internal fun rememberEffectiveQualifier(
    qualifier: DpQualifier,
    inverter: Inverter,
): DpQualifier {
    if (inverter == Inverter.DEFAULT) return qualifier
    val orientation = LocalConfiguration.current.orientation
    return remember(orientation, qualifier, inverter) {
        effectiveDpQualifier(orientation, qualifier, inverter)
    }
}

@Composable
internal fun rememberDimenResourceId(
    actualQualifier: DpQualifier,
    value: Int,
): Int {
    val context = LocalContext.current
    return remember(actualQualifier, value, context.packageName) {
        resolveDimenResourceId(context, actualQualifier, value)
    }
}

/** Builds `_Nsdp` / `_Nhdp` / `_Nwdp` / `_minusNsdp` and resolves via [DimenResourceIdCache]. */
internal fun resolveDimenResourceId(
    context: Context,
    actualQualifier: DpQualifier,
    value: Int,
): Int {
    if (value == 0) return 0
    val axis = when (actualQualifier) {
        DpQualifier.HEIGHT -> "hdp"
        DpQualifier.WIDTH -> "wdp"
        DpQualifier.SMALL_WIDTH -> "sdp"
    }
    val dimenName =
        if (value < 0) "_minus${abs(value)}$axis"
        else "_${value}$axis"
    return DimenResourceIdCache.getOrResolve(
        context.resources,
        context.packageName,
        dimenName,
    )
}

/**
 * Reads only AR-relevant configuration fields so remember keys ignore locale/uiMode noise
 * in the remembered adjustment (still requires a LocalConfiguration read to invalidate on size/dpi).
 */
@Composable
internal fun rememberAspectRatioAdjustment(actualQualifier: DpQualifier): Float {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val sw = configuration.smallestScreenWidthDp
    val widthDp = configuration.screenWidthDp
    val heightDp = configuration.screenHeightDp
    val densityDpi = configuration.densityDpi
    return remember(sw, widthDp, heightDp, densityDpi, actualQualifier, context) {
        AppDimensSdpsFactors.ensureUpToDate(context)
        AppDimensSdpsFactors.adjustmentForQualifier(actualQualifier)
    }
}
