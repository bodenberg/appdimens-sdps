package com.appdimens.sdps

import android.content.Context
import android.content.res.Configuration
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.appdimens.sdps.code.DimenSdp
import com.appdimens.sdps.core.AppDimensSdpsFactors
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.abs

@RunWith(AndroidJUnit4::class)
class AppDimensSdpsAspectRatioInstrumentedTest {

    private val epsilonDpPx = 0.06f // EN margin for float/layout rounding PT margem por float/arredondamento

    @Before
    fun resetFactorsCache() {
        AppDimensSdpsFactors.resetAdjustmentCacheForTestsOnly()
    }

    private fun overlayContext(
        smallestWidthDp: Int,
        screenWidthDp: Int,
        screenHeightDp: Int,
        densityDpiOverride: Int? = null,
    ): Context {
        val base = InstrumentationRegistry.getInstrumentation().targetContext
        val cfg = Configuration(base.resources.configuration)
        cfg.smallestScreenWidthDp = smallestWidthDp
        cfg.screenWidthDp = screenWidthDp
        cfg.screenHeightDp = screenHeightDp
        densityDpiOverride?.let { cfg.densityDpi = it }
        return base.createConfigurationContext(cfg)
    }

    @Test
    fun ensureUpToDate_doesNotThrow_whenOneDimenResourcesExist() {
        val ctx = overlayContext(smallestWidthDp = 411, screenWidthDp = 411, screenHeightDp = 890)
        AppDimensSdpsFactors.ensureUpToDate(ctx)
    }

    @Test
    fun sameConfigurationSignature_preservesVolatileAdjustments() {
        val ctx = overlayContext(420, 420, 915, 460)
        AppDimensSdpsFactors.ensureUpToDate(ctx)
        val sw1 = AppDimensSdpsFactors.arAdjustmentSw
        val w1 = AppDimensSdpsFactors.arAdjustmentW
        AppDimensSdpsFactors.ensureUpToDate(ctx)
        assertEquals(sw1, AppDimensSdpsFactors.arAdjustmentSw, epsilonDpPx)
        assertEquals(w1, AppDimensSdpsFactors.arAdjustmentW, epsilonDpPx)
    }

    @Test
    fun swap_screenWidthDp_and_screenHeightDp_swapsWandH_AdjustmentsLeavesSwTracked() {
        val dpi = InstrumentationRegistry.getInstrumentation().targetContext.resources.configuration.densityDpi
        val ctxA = overlayContext(411, 411, 900, dpi)
        AppDimensSdpsFactors.ensureUpToDate(ctxA)
        val swA = AppDimensSdpsFactors.arAdjustmentSw
        val wA = AppDimensSdpsFactors.arAdjustmentW
        val hA = AppDimensSdpsFactors.arAdjustmentH

        AppDimensSdpsFactors.resetAdjustmentCacheForTestsOnly()

        val ctxB = overlayContext(411, 900, 411, dpi)
        AppDimensSdpsFactors.ensureUpToDate(ctxB)
        assertEquals(swA, AppDimensSdpsFactors.arAdjustmentSw, epsilonDpPx)
        assertEquals(wA, AppDimensSdpsFactors.arAdjustmentH, 0.25f)
        assertEquals(hA, AppDimensSdpsFactors.arAdjustmentW, 0.25f)
    }

    @Test
    fun referencePortrait300_ratio178_sdp_near_sdpa() {
        val dpi = InstrumentationRegistry.getInstrumentation().targetContext.resources.configuration.densityDpi
        val ctx = overlayContext(smallestWidthDp = 300, screenWidthDp = 300, screenHeightDp = 534, dpi)
        AppDimensSdpsFactors.ensureUpToDate(ctx)
        assertTrue(abs(AppDimensSdpsFactors.arAdjustmentSw - 1f) < 0.035f)

        val pxBase = DimenSdp.sdp(ctx, 16)
        val pxAr = DimenSdp.sdpa(ctx, 16)
        assertEquals(pxBase, pxAr, pxBase.coerceAtLeast(1f) * 0.02f + epsilonDpPx)
    }

    @Test
    fun aspectRatioProducesDifferentScaling_whenAwayFromUnity() {
        val dpi = InstrumentationRegistry.getInstrumentation().targetContext.resources.configuration.densityDpi
        val ctx = overlayContext(480, 480, 960, dpi)
        AppDimensSdpsFactors.ensureUpToDate(ctx)
        val pxBase = DimenSdp.sdp(ctx, 32)
        val pxAr = DimenSdp.sdpa(ctx, 32)
        assertTrue(kotlin.math.abs(pxBase - pxAr) > 0.5f)
    }
}
