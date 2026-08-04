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
