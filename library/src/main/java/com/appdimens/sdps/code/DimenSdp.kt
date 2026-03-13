/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens-sdps.git
 * Date: 2025-10-04
 *
 * Library: AppDimens
 *
 * Description:
 * The AppDimens library is a dimension management system that automatically
 * adjusts Dp, Sp, and Px values in a responsive and mathematically refined way,
 * ensuring layout consistency across any screen size or ratio.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appdimens.sdps.code

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import com.appdimens.sdps.common.DpQualifier
import com.appdimens.sdps.common.DpQualifierEntry
import com.appdimens.sdps.common.Inverter
import com.appdimens.sdps.common.Orientation
import com.appdimens.sdps.common.UiModeType

/**
 * EN
 * Utility object for handling SDP (Scalable Dp) dimensions.
 *
 * PT
 * Objeto utilitário para manipulação de dimensões SDP (Scalable Dp).
 */
object DimenSdp {
    private const val MIN_VALUE =
        -300 // EN Minimum allowed SDP value. / PT Valor mínimo permitido para SDP.
    private const val MAX_VALUE =
        600 // EN Maximum allowed SDP value. / PT Valor máximo permitido para SDP.
    private const val DIMEN_TYPE =
        "dimen" // EN The resource type for dimensions. / PT O tipo de recurso para dimensões.

    /**
     * EN
     * Gets the dimension in pixels from an SDP value.
     *
     * PT
     * Obtém a dimensão em pixels a partir de um valor SDP.
     *
     * @param context The application context.
     * @param dpQualifier DpQualifier.
     * @param value The SDP value (-300 to 600).
     * @param inverter The inverter type to dynamically adapt scaling (default is Inverter.DEFAULT).
     * @return The dimension in pixels, or 0f if not found.
     */
    @JvmStatic
    @JvmOverloads
    fun getDimensionInPx(context: Context, dpQualifier: DpQualifier, value: Int, inverter: Inverter = Inverter.DEFAULT): Float {
        if (value == 0) return 0f
        val resourceId = getResourceId(context, dpQualifier, value, inverter)
        return if (resourceId != 0) {
            context.resources.getDimension(resourceId)
        } else 0f
    }

    /**
     * EN
     * Gets the resource ID for an SDP value.
     *
     * PT
     * Obtém o ID do recurso para um valor SDP.
     *
     * @param context The application context.
     * @param dpQualifier DpQualifier.
     * @param value The SDP value (-300 to 600).
     * @param inverter The inverter type to dynamically adapt scaling (default is Inverter.DEFAULT).
     * @return The resource ID, or 0 if not found.
     */
    @JvmStatic
    @JvmOverloads
    @SuppressLint("DiscouragedApi")
    fun getResourceId(context: Context, dpQualifier: DpQualifier, value: Int, inverter: Inverter = Inverter.DEFAULT): Int {
        if (value == 0) return 0

        val configuration = context.resources.configuration
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        var actualQualifier = dpQualifier

        when (inverter) {
            Inverter.PH_TO_LW -> if (isLandscape && dpQualifier == DpQualifier.HEIGHT) actualQualifier = DpQualifier.WIDTH
            Inverter.PW_TO_LH -> if (isLandscape && dpQualifier == DpQualifier.WIDTH) actualQualifier = DpQualifier.HEIGHT
            Inverter.LH_TO_PW -> if (isPortrait && dpQualifier == DpQualifier.HEIGHT) actualQualifier = DpQualifier.WIDTH
            Inverter.LW_TO_PH -> if (isPortrait && dpQualifier == DpQualifier.WIDTH) actualQualifier = DpQualifier.HEIGHT
            Inverter.SW_TO_LH -> if (isLandscape && dpQualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.HEIGHT
            Inverter.SW_TO_LW -> if (isLandscape && dpQualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.WIDTH
            Inverter.SW_TO_PH -> if (isPortrait && dpQualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.HEIGHT
            Inverter.SW_TO_PW -> if (isPortrait && dpQualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.WIDTH
            Inverter.DEFAULT -> {}
        }

        val safeValue = value.coerceIn(MIN_VALUE, MAX_VALUE)
        val sdpSuffix = when (actualQualifier) {
            DpQualifier.SMALL_WIDTH -> "sdp"
            DpQualifier.HEIGHT -> "hdp"
            DpQualifier.WIDTH -> "wdp"
        }
        val dimenName = buildResourceName(safeValue, sdpSuffix)

        return context.resources.getIdentifier(dimenName, DIMEN_TYPE, context.packageName)
    }

    // EN Extensions style functions similar to the compose equivalents for quick resolution.
    // PT Funções estilo extensão similares aos equivalentes do compose para resolução rápida.

    /**
     * EN
     * Quick resolution for Smallest Width (sdp).
     * Usage example: `DimenSdp.sdp(context, 16)`.
     *
     * PT
     * Resolução rápida para Smallest Width (sdp).
     * Exemplo de uso: `DimenSdp.sdp(context, 16)`.
     */
    @JvmStatic
    fun sdp(context: Context, value: Int): Float = getDimensionInPx(context, DpQualifier.SMALL_WIDTH, value)

    /**
     * EN
     * Quick resolution for Smallest Width (sdp), but
     * in portrait orientation it acts as Screen Height (hdp).
     * Usage example: `DimenSdp.sdpPh(context, 32)`.
     *
     * PT
     * Resolução rápida para Smallest Width (sdp), mas
     * na orientação retrato atua como Altura da Tela (hdp).
     * Exemplo de uso: `DimenSdp.sdpPh(context, 32)`.
     */
    @JvmStatic
    fun sdpPh(context: Context, value: Int): Float = getDimensionInPx(context, DpQualifier.SMALL_WIDTH, value, Inverter.SW_TO_PH)

    /**
     * EN
     * Quick resolution for Smallest Width (sdp), but
     * in landscape orientation it acts as Screen Height (hdp).
     * Usage example: `DimenSdp.sdpLh(context, 32)`.
     *
     * PT
     * Resolução rápida para Smallest Width (sdp), mas
     * na orientação paisagem atua como Altura da Tela (hdp).
     * Exemplo de uso: `DimenSdp.sdpLh(context, 32)`.
     */
    @JvmStatic
    fun sdpLh(context: Context, value: Int): Float = getDimensionInPx(context, DpQualifier.SMALL_WIDTH, value, Inverter.SW_TO_LH)

    /**
     * EN
     * Quick resolution for Smallest Width (sdp), but
     * in portrait orientation it acts as Screen Width (wdp).
     * Usage example: `DimenSdp.sdpPw(context, 32)`.
     *
     * PT
     * Resolução rápida para Smallest Width (sdp), mas
     * na orientação retrato atua como Largura da Tela (wdp).
     * Exemplo de uso: `DimenSdp.sdpPw(context, 32)`.
     */
    @JvmStatic
    fun sdpPw(context: Context, value: Int): Float = getDimensionInPx(context, DpQualifier.SMALL_WIDTH, value, Inverter.SW_TO_PW)

    /**
     * EN
     * Quick resolution for Smallest Width (sdp), but
     * in landscape orientation it acts as Screen Width (wdp).
     * Usage example: `DimenSdp.sdpLw(context, 32)`.
     *
     * PT
     * Resolução rápida para Smallest Width (sdp), mas
     * na orientação paisagem atua como Largura da Tela (wdp).
     * Exemplo de uso: `DimenSdp.sdpLw(context, 32)`.
     */
    @JvmStatic
    fun sdpLw(context: Context, value: Int): Float = getDimensionInPx(context, DpQualifier.SMALL_WIDTH, value, Inverter.SW_TO_LW)

    /**
     * EN
     * Quick resolution for Screen Height (hdp).
     * Usage example: `DimenSdp.hdp(context, 32)`.
     *
     * PT
     * Resolução rápida para Altura da Tela (hdp).
     * Exemplo de uso: `DimenSdp.hdp(context, 32)`.
     */
    @JvmStatic
    fun hdp(context: Context, value: Int): Float = getDimensionInPx(context, DpQualifier.HEIGHT, value)

    /**
     * EN
     * Quick resolution for Screen Height (hdp), but
     * in landscape orientation it acts as Screen Width (wdp).
     * Usage example: `DimenSdp.hdpLw(context, 32)`.
     *
     * PT
     * Resolução rápida para Altura da Tela (hdp), mas
     * na orientação paisagem atua como Largura da Tela (wdp).
     * Exemplo de uso: `DimenSdp.hdpLw(context, 32)`.
     */
    @JvmStatic
    fun hdpLw(context: Context, value: Int): Float = getDimensionInPx(context, DpQualifier.HEIGHT, value, Inverter.PH_TO_LW)

    /**
     * EN
     * Quick resolution for Screen Height (hdp), but
     * in portrait orientation it acts as Screen Width (wdp).
     * Usage example: `DimenSdp.hdpPw(context, 32)`.
     *
     * PT
     * Resolução rápida para Altura da Tela (hdp), mas
     * na orientação retrato atua como Largura da Tela (wdp).
     * Exemplo de uso: `DimenSdp.hdpPw(context, 32)`.
     */
    @JvmStatic
    fun hdpPw(context: Context, value: Int): Float = getDimensionInPx(context, DpQualifier.HEIGHT, value, Inverter.LH_TO_PW)

    /**
     * EN
     * Quick resolution for Screen Width (wdp).
     * Usage example: `DimenSdp.wdp(context, 100)`.
     *
     * PT
     * Resolução rápida para Largura da Tela (wdp).
     * Exemplo de uso: `DimenSdp.wdp(context, 100)`.
     */
    @JvmStatic
    fun wdp(context: Context, value: Int): Float = getDimensionInPx(context, DpQualifier.WIDTH, value)

    /**
     * EN
     * Quick resolution for Screen Width (wdp), but
     * in landscape orientation it acts as Screen Height (hdp).
     * Usage example: `DimenSdp.wdpLh(context, 100)`.
     *
     * PT
     * Resolução rápida para Largura da Tela (wdp), mas
     * na orientação paisagem atua como Altura da Tela (hdp).
     * Exemplo de uso: `DimenSdp.wdpLh(context, 100)`.
     */
    @JvmStatic
    fun wdpLh(context: Context, value: Int): Float = getDimensionInPx(context, DpQualifier.WIDTH, value, Inverter.PW_TO_LH)

    /**
     * EN
     * Quick resolution for Screen Width (wdp), but
     * in portrait orientation it acts as Screen Height (hdp).
     * Usage example: `DimenSdp.wdpPh(context, 100)`.
     *
     * PT
     * Resolução rápida para Largura da Tela (wdp), mas
     * na orientação retrato atua como Altura da Tela (hdp).
     * Exemplo de uso: `DimenSdp.wdpPh(context, 100)`.
     */
    @JvmStatic
    fun wdpPh(context: Context, value: Int): Float = getDimensionInPx(context, DpQualifier.WIDTH, value, Inverter.LW_TO_PH)

    // EN Resource ID variants of the above extensions.
    // PT Variantes que retornam o ID de recurso das extensões acima.

    /**
     * EN
     * Quick resolution for Smallest Width resource ID (sdpRes).
     * Usage example: `DimenSdp.sdpRes(context, 16)`.
     *
     * PT
     * Resolução rápida para ID de recurso Smallest Width (sdpRes).
     * Exemplo de uso: `DimenSdp.sdpRes(context, 16)`.
     */
    @JvmStatic
    fun sdpRes(context: Context, value: Int): Int = getResourceId(context, DpQualifier.SMALL_WIDTH, value)

    /**
     * EN
     * Quick resolution for Smallest Width resource ID (sdpRes), but
     * in portrait orientation it acts as Screen Height resource ID (hdpRes).
     * Usage example: `DimenSdp.sdpPhRes(context, 32)`.
     *
     * PT
     * Resolução rápida para ID de recurso Smallest Width (sdpRes), mas
     * na orientação retrato atua como Altura da Tela (hdpRes).
     * Exemplo de uso: `DimenSdp.sdpPhRes(context, 32)`.
     */
    @JvmStatic
    fun sdpPhRes(context: Context, value: Int): Int = getResourceId(context, DpQualifier.SMALL_WIDTH, value, Inverter.SW_TO_PH)

    /**
     * EN
     * Quick resolution for Smallest Width resource ID (sdpRes), but
     * in landscape orientation it acts as Screen Height resource ID (hdpRes).
     * Usage example: `DimenSdp.sdpLhRes(context, 32)`.
     *
     * PT
     * Resolução rápida para ID de recurso Smallest Width (sdpRes), mas
     * na orientação paisagem atua como Altura da Tela (hdpRes).
     * Exemplo de uso: `DimenSdp.sdpLhRes(context, 32)`.
     */
    @JvmStatic
    fun sdpLhRes(context: Context, value: Int): Int = getResourceId(context, DpQualifier.SMALL_WIDTH, value, Inverter.SW_TO_LH)

    /**
     * EN
     * Quick resolution for Smallest Width resource ID (sdpRes), but
     * in portrait orientation it acts as Screen Width resource ID (wdpRes).
     * Usage example: `DimenSdp.sdpPwRes(context, 32)`.
     *
     * PT
     * Resolução rápida para ID de recurso Smallest Width (sdpRes), mas
     * na orientação retrato atua como Largura da Tela (wdpRes).
     * Exemplo de uso: `DimenSdp.sdpPwRes(context, 32)`.
     */
    @JvmStatic
    fun sdpPwRes(context: Context, value: Int): Int = getResourceId(context, DpQualifier.SMALL_WIDTH, value, Inverter.SW_TO_PW)

    /**
     * EN
     * Quick resolution for Smallest Width resource ID (sdpRes), but
     * in landscape orientation it acts as Screen Width resource ID (wdpRes).
     * Usage example: `DimenSdp.sdpLwRes(context, 32)`.
     *
     * PT
     * Resolução rápida para ID de recurso Smallest Width (sdpRes), mas
     * na orientação paisagem atua como Largura da Tela (wdpRes).
     * Exemplo de uso: `DimenSdp.sdpLwRes(context, 32)`.
     */
    @JvmStatic
    fun sdpLwRes(context: Context, value: Int): Int = getResourceId(context, DpQualifier.SMALL_WIDTH, value, Inverter.SW_TO_LW)

    /**
     * EN
     * Quick resolution for Screen Height resource ID (hdpRes).
     * Usage example: `DimenSdp.hdpRes(context, 32)`.
     *
     * PT
     * Resolução rápida para ID de recurso Altura da Tela (hdpRes).
     * Exemplo de uso: `DimenSdp.hdpRes(context, 32)`.
     */
    @JvmStatic
    fun hdpRes(context: Context, value: Int): Int = getResourceId(context, DpQualifier.HEIGHT, value)

    /**
     * EN
     * Quick resolution for Screen Height resource ID (hdpRes), but
     * in landscape orientation it acts as Screen Width resource ID (wdpRes).
     * Usage example: `DimenSdp.hdpLwRes(context, 32)`.
     *
     * PT
     * Resolução rápida para ID de recurso Altura da Tela (hdpRes), mas
     * na orientação paisagem atua como Largura da Tela (wdpRes).
     * Exemplo de uso: `DimenSdp.hdpLwRes(context, 32)`.
     */
    @JvmStatic
    fun hdpLwRes(context: Context, value: Int): Int = getResourceId(context, DpQualifier.HEIGHT, value, Inverter.PH_TO_LW)

    /**
     * EN
     * Quick resolution for Screen Height resource ID (hdpRes), but
     * in portrait orientation it acts as Screen Width resource ID (wdpRes).
     * Usage example: `DimenSdp.hdpPwRes(context, 32)`.
     *
     * PT
     * Resolução rápida para ID de recurso Altura da Tela (hdpRes), mas
     * na orientação retrato atua como Largura da Tela (wdpRes).
     * Exemplo de uso: `DimenSdp.hdpPwRes(context, 32)`.
     */
    @JvmStatic
    fun hdpPwRes(context: Context, value: Int): Int = getResourceId(context, DpQualifier.HEIGHT, value, Inverter.LH_TO_PW)

    /**
     * EN
     * Quick resolution for Screen Width resource ID (wdpRes).
     * Usage example: `DimenSdp.wdpRes(context, 100)`.
     *
     * PT
     * Resolução rápida para ID de recurso Largura da Tela (wdpRes).
     * Exemplo de uso: `DimenSdp.wdpRes(context, 100)`.
     */
    @JvmStatic
    fun wdpRes(context: Context, value: Int): Int = getResourceId(context, DpQualifier.WIDTH, value)

    /**
     * EN
     * Quick resolution for Screen Width resource ID (wdpRes), but
     * in landscape orientation it acts as Screen Height resource ID (hdpRes).
     * Usage example: `DimenSdp.wdpLhRes(context, 100)`.
     *
     * PT
     * Resolução rápida para ID de recurso Largura da Tela (wdpRes), mas
     * na orientação paisagem atua como Altura da Tela (hdpRes).
     * Exemplo de uso: `DimenSdp.wdpLhRes(context, 100)`.
     */
    @JvmStatic
    fun wdpLhRes(context: Context, value: Int): Int = getResourceId(context, DpQualifier.WIDTH, value, Inverter.PW_TO_LH)

    /**
     * EN
     * Quick resolution for Screen Width resource ID (wdpRes), but
     * in portrait orientation it acts as Screen Height resource ID (hdpRes).
     * Usage example: `DimenSdp.wdpPhRes(context, 100)`.
     *
     * PT
     * Resolução rápida para ID de recurso Largura da Tela (wdpRes), mas
     * na orientação retrato atua como Altura da Tela (hdpRes).
     * Exemplo de uso: `DimenSdp.wdpPhRes(context, 100)`.
     */
    @JvmStatic
    fun wdpPhRes(context: Context, value: Int): Int = getResourceId(context, DpQualifier.WIDTH, value, Inverter.LW_TO_PH)

    /**
     * EN
     * Builds the resource name for a given SDP value.
     * For negative values, it uses the "_minus" prefix (e.g., _minus10sdp).
     * For positive values, it uses a "_" prefix (e.g., _10sdp).
     *
     * PT
     * Constrói o nome do recurso para um determinado valor SDP.
     * Para valores negativos, usa o prefixo "_minus" (ex: _minus10sdp).
     * Para valores positivos, usa o prefixo "_" (ex: _10sdp).
     *
     * @param value The integer value.
     * @return The formatted resource name.
     */
    private fun buildResourceName(value: Int, sdpSuffix: String): String = when {
        value < 0 -> "_minus${kotlin.math.abs(value)}$sdpSuffix"
        else -> "_${value}$sdpSuffix"
    }

    /**
     * EN
     * Starts the build chain for the custom dimension DimenScaled from a base Int.
     * Usage example: `DimenSdp.scaled(100).screen(...)`.
     *
     * PT
     * Inicia a cadeia de construção para a dimensão customizada DimenScaled a partir de um Int base.
     * Exemplo de uso: `DimenSdp.scaled(100).screen(...)`.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): DimenScaled = DimenScaled(initialBaseValue)

    // EN Rotation facilitator functions for code.
    // PT Funções facilitadoras de rotação para código.

    /**
     * EN
     * Facilitator for Smallest Width (sdp) with rotation override.
     * Uses the base value by default, but when the device is in the specified [orientation],
     * it uses [rotationValue] scaled with the given [finalQualifierResolver].
     * Usage example: `DimenSdp.sdpRotate(context, 30, 45, DpQualifier.SMALL_WIDTH, Orientation.LANDSCAPE)`.
     *
     * PT
     * Facilitador para Smallest Width (sdp) com substituição por rotação.
     * Usa o valor base por padrão, mas quando o dispositivo está na [orientation] especificada,
     * usa [rotationValue] escalado com o [finalQualifierResolver] dado.
     * Exemplo de uso: `DimenSdp.sdpRotate(context, 30, 45, DpQualifier.SMALL_WIDTH, Orientation.LANDSCAPE)`.
     */
    @JvmStatic
    @JvmOverloads
    fun sdpRotate(context: Context, value: Int, rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE): Float {
        val configuration = context.resources.configuration
        val isTarget = when (orientation) {
            Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            else -> false
        }
        return if (isTarget) getDimensionInPx(context, finalQualifierResolver, rotationValue)
        else getDimensionInPx(context, DpQualifier.SMALL_WIDTH, value)
    }

    /**
     * EN
     * Facilitator for Screen Height (hdp) with rotation override.
     *
     * PT
     * Facilitador para Altura da Tela (hdp) com substituição por rotação.
     */
    @JvmStatic
    @JvmOverloads
    fun hdpRotate(context: Context, value: Int, rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE): Float {
        val configuration = context.resources.configuration
        val isTarget = when (orientation) {
            Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            else -> false
        }
        return if (isTarget) getDimensionInPx(context, finalQualifierResolver, rotationValue)
        else getDimensionInPx(context, DpQualifier.HEIGHT, value)
    }

    /**
     * EN
     * Facilitator for Screen Width (wdp) with rotation override.
     *
     * PT
     * Facilitador para Largura da Tela (wdp) com substituição por rotação.
     */
    @JvmStatic
    @JvmOverloads
    fun wdpRotate(context: Context, value: Int, rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE): Float {
        val configuration = context.resources.configuration
        val isTarget = when (orientation) {
            Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            else -> false
        }
        return if (isTarget) getDimensionInPx(context, finalQualifierResolver, rotationValue)
        else getDimensionInPx(context, DpQualifier.WIDTH, value)
    }

    // EN UiModeType facilitator functions for code.
    // PT Funções facilitadoras de UiModeType para código.

    /**
     * EN
     * Facilitator for Smallest Width (sdp) with UiModeType override.
     * Usage example: `DimenSdp.sdpMode(context, 30, 50, UiModeType.TELEVISION)`.
     *
     * PT
     * Facilitador para Smallest Width (sdp) com substituição por UiModeType.
     * Exemplo de uso: `DimenSdp.sdpMode(context, 30, 50, UiModeType.TELEVISION)`.
     */
    @JvmStatic
    @JvmOverloads
    fun sdpMode(context: Context, value: Int, modeValue: Int, uiModeType: UiModeType, foldingFeature: androidx.window.layout.FoldingFeature? = null, finalQualifierResolver: DpQualifier? = null): Float {
        val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
        return if (currentUiModeType == uiModeType) getDimensionInPx(context, finalQualifierResolver ?: DpQualifier.SMALL_WIDTH, modeValue)
        else getDimensionInPx(context, DpQualifier.SMALL_WIDTH, value)
    }

    /**
     * EN
     * Facilitator for Screen Height (hdp) with UiModeType override.
     *
     * PT
     * Facilitador para Altura da Tela (hdp) com substituição por UiModeType.
     */
    @JvmStatic
    @JvmOverloads
    fun hdpMode(context: Context, value: Int, modeValue: Int, uiModeType: UiModeType, foldingFeature: androidx.window.layout.FoldingFeature? = null, finalQualifierResolver: DpQualifier? = null): Float {
        val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
        return if (currentUiModeType == uiModeType) getDimensionInPx(context, finalQualifierResolver ?: DpQualifier.HEIGHT, modeValue)
        else getDimensionInPx(context, DpQualifier.HEIGHT, value)
    }

    /**
     * EN
     * Facilitator for Screen Width (wdp) with UiModeType override.
     *
     * PT
     * Facilitador para Largura da Tela (wdp) com substituição por UiModeType.
     */
    @JvmStatic
    @JvmOverloads
    fun wdpMode(context: Context, value: Int, modeValue: Int, uiModeType: UiModeType, foldingFeature: androidx.window.layout.FoldingFeature? = null, finalQualifierResolver: DpQualifier? = null): Float {
        val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
        return if (currentUiModeType == uiModeType) getDimensionInPx(context, finalQualifierResolver ?: DpQualifier.WIDTH, modeValue)
        else getDimensionInPx(context, DpQualifier.WIDTH, value)
    }

    // EN DpQualifier facilitator functions for code.
    // PT Funções facilitadoras de DpQualifier para código.

    /**
     * EN
     * Facilitator for Smallest Width (sdp) with DpQualifier override.
     * Usage example: `DimenSdp.sdpQualifier(context, 30, 50, DpQualifier.SMALL_WIDTH, 600)`.
     *
     * PT
     * Facilitador para Smallest Width (sdp) com substituição por DpQualifier.
     * Exemplo de uso: `DimenSdp.sdpQualifier(context, 30, 50, DpQualifier.SMALL_WIDTH, 600)`.
     */
    @JvmStatic
    @JvmOverloads
    @SuppressLint("ConfigurationScreenWidthHeight")
    fun sdpQualifier(context: Context, value: Int, qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
        val configuration = context.resources.configuration
        val screenValue = when (qualifierType) {
            DpQualifier.SMALL_WIDTH -> configuration.smallestScreenWidthDp.toFloat()
            DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
            DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
        }
        return if (screenValue >= qualifierValue) getDimensionInPx(context, finalQualifierResolver ?: DpQualifier.SMALL_WIDTH, qualifiedValue)
        else getDimensionInPx(context, DpQualifier.SMALL_WIDTH, value)
    }

    /**
     * EN
     * Facilitator for Screen Height (hdp) with DpQualifier override.
     *
     * PT
     * Facilitador para Altura da Tela (hdp) com substituição por DpQualifier.
     */
    @JvmStatic
    @JvmOverloads
    @SuppressLint("ConfigurationScreenWidthHeight")
    fun hdpQualifier(context: Context, value: Int, qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
        val configuration = context.resources.configuration
        val screenValue = when (qualifierType) {
            DpQualifier.SMALL_WIDTH -> configuration.smallestScreenWidthDp.toFloat()
            DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
            DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
        }
        return if (screenValue >= qualifierValue) getDimensionInPx(context, finalQualifierResolver ?: DpQualifier.HEIGHT, qualifiedValue)
        else getDimensionInPx(context, DpQualifier.HEIGHT, value)
    }

    /**
     * EN
     * Facilitator for Screen Width (wdp) with DpQualifier override.
     *
     * PT
     * Facilitador para Largura da Tela (wdp) com substituição por DpQualifier.
     */
    @JvmStatic
    @JvmOverloads
    @SuppressLint("ConfigurationScreenWidthHeight")
    fun wdpQualifier(context: Context, value: Int, qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
        val configuration = context.resources.configuration
        val screenValue = when (qualifierType) {
            DpQualifier.SMALL_WIDTH -> configuration.smallestScreenWidthDp.toFloat()
            DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
            DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
        }
        return if (screenValue >= qualifierValue) getDimensionInPx(context, finalQualifierResolver ?: DpQualifier.WIDTH, qualifiedValue)
        else getDimensionInPx(context, DpQualifier.WIDTH, value)
    }

    // EN UiModeType + DpQualifier combined facilitator functions for code.
    // PT Funções facilitadoras combinadas UiModeType + DpQualifier para código.

    /**
     * EN
     * Facilitator for Smallest Width (sdp) with combined UiModeType + DpQualifier override.
     * Usage example: `DimenSdp.sdpScreen(context, 30, 50, UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600)`.
     *
     * PT
     * Facilitador para Smallest Width (sdp) com substituição combinada UiModeType + DpQualifier.
     * Exemplo de uso: `DimenSdp.sdpScreen(context, 30, 50, UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600)`.
     */
    @JvmStatic
    @JvmOverloads
    @SuppressLint("ConfigurationScreenWidthHeight")
    fun sdpScreen(context: Context, value: Int, screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, foldingFeature: androidx.window.layout.FoldingFeature? = null, finalQualifierResolver: DpQualifier? = null): Float {
        val configuration = context.resources.configuration
        val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
        val qualifierScreenValue = when (qualifierType) {
            DpQualifier.SMALL_WIDTH -> configuration.smallestScreenWidthDp.toFloat()
            DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
            DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
        }
        return if (currentUiModeType == uiModeType && qualifierScreenValue >= qualifierValue) getDimensionInPx(context, finalQualifierResolver ?: DpQualifier.SMALL_WIDTH, screenValue)
        else getDimensionInPx(context, DpQualifier.SMALL_WIDTH, value)
    }

    /**
     * EN
     * Facilitator for Screen Height (hdp) with combined UiModeType + DpQualifier override.
     *
     * PT
     * Facilitador para Altura da Tela (hdp) com substituição combinada UiModeType + DpQualifier.
     */
    @JvmStatic
    @JvmOverloads
    @SuppressLint("ConfigurationScreenWidthHeight")
    fun hdpScreen(context: Context, value: Int, screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, foldingFeature: androidx.window.layout.FoldingFeature? = null, finalQualifierResolver: DpQualifier? = null): Float {
        val configuration = context.resources.configuration
        val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
        val qualifierScreenValue = when (qualifierType) {
            DpQualifier.SMALL_WIDTH -> configuration.smallestScreenWidthDp.toFloat()
            DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
            DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
        }
        return if (currentUiModeType == uiModeType && qualifierScreenValue >= qualifierValue) getDimensionInPx(context, finalQualifierResolver ?: DpQualifier.HEIGHT, screenValue)
        else getDimensionInPx(context, DpQualifier.HEIGHT, value)
    }

    /**
     * EN
     * Facilitator for Screen Width (wdp) with combined UiModeType + DpQualifier override.
     *
     * PT
     * Facilitador para Largura da Tela (wdp) com substituição combinada UiModeType + DpQualifier.
     */
    @JvmStatic
    @JvmOverloads
    @SuppressLint("ConfigurationScreenWidthHeight")
    fun wdpScreen(context: Context, value: Int, screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, foldingFeature: androidx.window.layout.FoldingFeature? = null, finalQualifierResolver: DpQualifier? = null): Float {
        val configuration = context.resources.configuration
        val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
        val qualifierScreenValue = when (qualifierType) {
            DpQualifier.SMALL_WIDTH -> configuration.smallestScreenWidthDp.toFloat()
            DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
            DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
        }
        return if (currentUiModeType == uiModeType && qualifierScreenValue >= qualifierValue) getDimensionInPx(context, finalQualifierResolver ?: DpQualifier.WIDTH, screenValue)
        else getDimensionInPx(context, DpQualifier.WIDTH, value)
    }

}

