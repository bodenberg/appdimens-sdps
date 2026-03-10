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
     * Usage example: `DimenSdp.hdp_lw(context, 32)`.
     *
     * PT
     * Resolução rápida para Altura da Tela (hdp), mas
     * na orientação paisagem atua como Largura da Tela (wdp).
     * Exemplo de uso: `DimenSdp.hdp_lw(context, 32)`.
     */
    @JvmStatic
    fun hdp_lw(context: Context, value: Int): Float = getDimensionInPx(context, DpQualifier.HEIGHT, value, Inverter.PH_TO_LW)

    /**
     * EN
     * Quick resolution for Screen Height (hdp), but
     * in portrait orientation it acts as Screen Width (wdp).
     * Usage example: `DimenSdp.hdp_pw(context, 32)`.
     *
     * PT
     * Resolução rápida para Altura da Tela (hdp), mas
     * na orientação retrato atua como Largura da Tela (wdp).
     * Exemplo de uso: `DimenSdp.hdp_pw(context, 32)`.
     */
    @JvmStatic
    fun hdp_pw(context: Context, value: Int): Float = getDimensionInPx(context, DpQualifier.HEIGHT, value, Inverter.LH_TO_PW)

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
     * Usage example: `DimenSdp.wdp_lh(context, 100)`.
     *
     * PT
     * Resolução rápida para Largura da Tela (wdp), mas
     * na orientação paisagem atua como Altura da Tela (hdp).
     * Exemplo de uso: `DimenSdp.wdp_lh(context, 100)`.
     */
    @JvmStatic
    fun wdp_lh(context: Context, value: Int): Float = getDimensionInPx(context, DpQualifier.WIDTH, value, Inverter.PW_TO_LH)

    /**
     * EN
     * Quick resolution for Screen Width (wdp), but
     * in portrait orientation it acts as Screen Height (hdp).
     * Usage example: `DimenSdp.wdp_ph(context, 100)`.
     *
     * PT
     * Resolução rápida para Largura da Tela (wdp), mas
     * na orientação retrato atua como Altura da Tela (hdp).
     * Exemplo de uso: `DimenSdp.wdp_ph(context, 100)`.
     */
    @JvmStatic
    fun wdp_ph(context: Context, value: Int): Float = getDimensionInPx(context, DpQualifier.WIDTH, value, Inverter.LW_TO_PH)

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
     * Usage example: `DimenSdp.hdp_lwRes(context, 32)`.
     *
     * PT
     * Resolução rápida para ID de recurso Altura da Tela (hdpRes), mas
     * na orientação paisagem atua como Largura da Tela (wdpRes).
     * Exemplo de uso: `DimenSdp.hdp_lwRes(context, 32)`.
     */
    @JvmStatic
    fun hdp_lwRes(context: Context, value: Int): Int = getResourceId(context, DpQualifier.HEIGHT, value, Inverter.PH_TO_LW)

    /**
     * EN
     * Quick resolution for Screen Height resource ID (hdpRes), but
     * in portrait orientation it acts as Screen Width resource ID (wdpRes).
     * Usage example: `DimenSdp.hdp_pwRes(context, 32)`.
     *
     * PT
     * Resolução rápida para ID de recurso Altura da Tela (hdpRes), mas
     * na orientação retrato atua como Largura da Tela (wdpRes).
     * Exemplo de uso: `DimenSdp.hdp_pwRes(context, 32)`.
     */
    @JvmStatic
    fun hdp_pwRes(context: Context, value: Int): Int = getResourceId(context, DpQualifier.HEIGHT, value, Inverter.LH_TO_PW)

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
     * Usage example: `DimenSdp.wdp_lhRes(context, 100)`.
     *
     * PT
     * Resolução rápida para ID de recurso Largura da Tela (wdpRes), mas
     * na orientação paisagem atua como Altura da Tela (hdpRes).
     * Exemplo de uso: `DimenSdp.wdp_lhRes(context, 100)`.
     */
    @JvmStatic
    fun wdp_lhRes(context: Context, value: Int): Int = getResourceId(context, DpQualifier.WIDTH, value, Inverter.PW_TO_LH)

    /**
     * EN
     * Quick resolution for Screen Width resource ID (wdpRes), but
     * in portrait orientation it acts as Screen Height resource ID (hdpRes).
     * Usage example: `DimenSdp.wdp_phRes(context, 100)`.
     *
     * PT
     * Resolução rápida para ID de recurso Largura da Tela (wdpRes), mas
     * na orientação retrato atua como Altura da Tela (hdpRes).
     * Exemplo de uso: `DimenSdp.wdp_phRes(context, 100)`.
     */
    @JvmStatic
    fun wdp_phRes(context: Context, value: Int): Int = getResourceId(context, DpQualifier.WIDTH, value, Inverter.LW_TO_PH)

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
     * Starts the build chain for the custom dimension Scaled from a base Int.
     * Usage example: `DimenSdp.scaled(100).screen(...)`.
     *
     * PT
     * Inicia a cadeia de construção para a dimensão customizada Scaled a partir de um Int base.
     * Exemplo de uso: `DimenSdp.scaled(100).screen(...)`.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): Scaled = Scaled(initialBaseValue)


}

/**
 * EN Starts the build chain for the custom dimension Scaled from a base Int.
 * PT Inicia a cadeia de construção para a dimensão customizada Scaled a partir de um Int base.
 */
fun Int.scaledSdp(): Scaled = Scaled(this)

/**
 * EN
 * Represents a custom dimension entry with qualifiers and priority for code.
 *
 * PT
 * Representa uma entrada de dimensão customizada com qualificadores e prioridade para código.
 *
 * @param uiModeType The UI mode (CAR, TELEVISION, WATCH, NORMAL). Null for any mode.
 * @param dpQualifierEntry The Dp qualifier entry (type and value, e.g., SMALL_WIDTH > 600). Null if only UI mode is used.
 * @param orientation The screen orientation (LANDSCAPE, PORTRAIT, DEFAULT).
 * @param customValue The int value to be used if the condition is met.
 * @param finalQualifierResolver Optional dimension qualifier (e.g., HEIGHT) to be applied at resolution time.
 * @param priority The resolution priority. 1 is more specific (UI + Qualifier), 3 is less specific (Qualifier only).
 * @param inverter The inverter type to adapt scaling width/height on rotation changes (default is Inverter.DEFAULT).
 */
data class CustomSdpEntry(
    val uiModeType: UiModeType? = null,
    val dpQualifierEntry: DpQualifierEntry? = null,
    val orientation: Orientation = Orientation.DEFAULT,
    val customValue: Int,
    val finalQualifierResolver: DpQualifier? = null,
    val priority: Int,
    val inverter: Inverter = Inverter.DEFAULT
)

/**
 * EN
 * A class that allows defining custom dimensions based on screen qualifiers.
 * Resolves to the final dimension in pixels.
 *
 * PT
 * Classe que permite definir dimensões customizadas baseadas em qualificadores de tela.
 * Resolve para a dimensão final em pixels.
 */
class Scaled internal constructor(
    private val initialBaseValue: Int,
    private val sortedCustomEntries: List<CustomSdpEntry> = emptyList()
) {

    constructor(initialBaseValue: Int) : this(initialBaseValue, emptyList())

    private fun reorderEntries(newEntry: CustomSdpEntry): List<CustomSdpEntry> {
        return (sortedCustomEntries + newEntry).sortedWith(
            compareBy<CustomSdpEntry> { it.priority }
                .thenByDescending { it.dpQualifierEntry?.value ?: 0 }
        )
    }

    /**
     * EN Priority 1: Most specific qualifier - Combines UiModeType AND Dp Qualifier.
     * PT Prioridade 1: Qualificador mais específico - Combina UiModeType E Qualificador de Dp.
     */
    @JvmOverloads
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Int,
        customValue: Int,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation = Orientation.DEFAULT,
        inverter: Inverter = Inverter.DEFAULT
    ): Scaled {
        val entry = CustomSdpEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 1,
            inverter = inverter
        )
        return Scaled(initialBaseValue, reorderEntries(entry))
    }

    /**
     * EN Priority 2: UiModeType qualifier.
     * PT Prioridade 2: Qualificador de UiModeType.
     */
    @JvmOverloads
    fun screen(
        type: UiModeType, 
        customValue: Int, 
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation = Orientation.DEFAULT,
        inverter: Inverter = Inverter.DEFAULT
    ): Scaled {
        val entry = CustomSdpEntry(
            uiModeType = type,
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 2,
            inverter = inverter
        )
        return Scaled(initialBaseValue, reorderEntries(entry))
    }

    /**
     * EN Priority 3: Dp qualifier.
     * PT Prioridade 3: Qualificador de Dp.
     */
    @JvmOverloads
    fun screen(
        type: DpQualifier, 
        value: Int, 
        customValue: Int, 
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation = Orientation.DEFAULT,
        inverter: Inverter = Inverter.DEFAULT
    ): Scaled {
        val entry = CustomSdpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 3,
            inverter = inverter
        )
        return Scaled(initialBaseValue, reorderEntries(entry))
    }

    /**
     * EN Priority 4: Orientation.
     * PT Prioridade 4: Orientação.
     */
    @JvmOverloads
    fun screen(
        orientation: Orientation = Orientation.DEFAULT,
        customValue: Int,
        finalQualifierResolver: DpQualifier? = null,
        inverter: Inverter = Inverter.DEFAULT
    ): Scaled {
        val entry = CustomSdpEntry(
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 4,
            inverter = inverter
        )
        return Scaled(initialBaseValue, reorderEntries(entry))
    }

    private fun findMatchingEntry(context: Context): CustomSdpEntry? {
        val configuration = context.resources.configuration
        val currentUiModeType = UiModeType.fromConfiguration(context)

        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        return sortedCustomEntries.firstOrNull { entry ->
            val qualifierEntry = entry.dpQualifierEntry
            val uiModeMatch = entry.uiModeType == null || entry.uiModeType == currentUiModeType
            
            val orientationMatch = when (entry.orientation) {
                Orientation.DEFAULT -> true
                Orientation.LANDSCAPE -> isLandscape
                Orientation.PORTRAIT -> isPortrait
            }

            if (qualifierEntry != null) {
                val screenQualifierValue = when (qualifierEntry.type) {
                    DpQualifier.SMALL_WIDTH -> configuration.smallestScreenWidthDp.toFloat()
                    DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
                    DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
                }
                val qualifierMatch = screenQualifierValue >= qualifierEntry.value

                if (entry.priority == 1 && uiModeMatch && qualifierMatch && orientationMatch) return@firstOrNull true
                if (entry.priority == 3 && qualifierMatch && orientationMatch) return@firstOrNull true

                return@firstOrNull false
            } else {
                // EN Priority 2: Must match only uiModeMatch AND orientationMatch (without Dp qualifier).
                // PT Prioridade 2: Deve casar apenas uiModeMatch E orientationMatch (sem qualificador de Dp).
                if (entry.priority == 2 && uiModeMatch && orientationMatch) return@firstOrNull true

                // EN Priority 4: Must match only orientationMatch (without Dp qualifier).
                // PT Prioridade 4: Deve casar apenas orientationMatch (sem qualificador de Dp).
                if (entry.priority == 4 && orientationMatch) return@firstOrNull true

                return@firstOrNull false
            }
        }
    }

    private fun resolve(context: Context, qualifier: DpQualifier): Float {
        val foundEntry = findMatchingEntry(context)
        val valueToUse = foundEntry?.customValue ?: initialBaseValue
        val actualQualifier = foundEntry?.finalQualifierResolver ?: qualifier
        return DimenSdp.getDimensionInPx(context, actualQualifier, valueToUse, foundEntry?.inverter ?: Inverter.DEFAULT)
    }

    private fun resolveRes(context: Context, qualifier: DpQualifier): Int {
        val foundEntry = findMatchingEntry(context)
        val valueToUse = foundEntry?.customValue ?: initialBaseValue
        val actualQualifier = foundEntry?.finalQualifierResolver ?: qualifier
        return DimenSdp.getResourceId(context, actualQualifier, valueToUse, foundEntry?.inverter ?: Inverter.DEFAULT)
    }

    /**
     * EN Final dimension value resolved in pixels.
     * PT Valor da dimensão final resolvida em pixels.
     */
    fun sdp(context: Context): Float = resolve(context, DpQualifier.SMALL_WIDTH)
    
    /**
     * EN Final dimension value resolved in pixels.
     * PT Valor da dimensão final resolvida em pixels.
     */
    fun hdp(context: Context): Float = resolve(context, DpQualifier.HEIGHT)
    
    /**
     * EN Final dimension value resolved in pixels.
     * PT Valor da dimensão final resolvida em pixels.
     */
    fun wdp(context: Context): Float = resolve(context, DpQualifier.WIDTH)
    
    /**
     * EN Final dimension value resolved as resource ID.
     * PT Valor da dimensão final resolvida como ID do recurso.
     */
    fun sdpRes(context: Context): Int = resolveRes(context, DpQualifier.SMALL_WIDTH)
    
    /**
     * EN Final dimension value resolved as resource ID.
     * PT Valor da dimensão final resolvida como ID do recurso.
     */
    fun hdpRes(context: Context): Int = resolveRes(context, DpQualifier.HEIGHT)
    
    /**
     * EN Final dimension value resolved as resource ID.
     * PT Valor da dimensão final resolvida como ID do recurso.
     */
    fun wdpRes(context: Context): Int = resolveRes(context, DpQualifier.WIDTH)
}
