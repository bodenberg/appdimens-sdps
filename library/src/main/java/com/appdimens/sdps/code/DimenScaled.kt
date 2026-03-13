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

import android.content.Context
import android.content.res.Configuration
import com.appdimens.sdps.common.DpQualifier
import com.appdimens.sdps.common.DpQualifierEntry
import com.appdimens.sdps.common.Inverter
import com.appdimens.sdps.common.Orientation
import com.appdimens.sdps.common.UiModeType

/**
 * EN Starts the build chain for the custom dimension DimenScaled from a base Int.
 * PT Inicia a cadeia de construção para a dimensão customizada DimenScaled a partir de um Int base.
 */
fun Int.scaledSdp(): DimenScaled = DimenScaled(this)

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
class DimenScaled internal constructor(
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
    ): DimenScaled {
        val entry = CustomSdpEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 1,
            inverter = inverter
        )
        return DimenScaled(initialBaseValue, reorderEntries(entry))
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
    ): DimenScaled {
        val entry = CustomSdpEntry(
            uiModeType = type,
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 2,
            inverter = inverter
        )
        return DimenScaled(initialBaseValue, reorderEntries(entry))
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
    ): DimenScaled {
        val entry = CustomSdpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 3,
            inverter = inverter
        )
        return DimenScaled(initialBaseValue, reorderEntries(entry))
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
    ): DimenScaled {
        val entry = CustomSdpEntry(
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 4,
            inverter = inverter
        )
        return DimenScaled(initialBaseValue, reorderEntries(entry))
    }

    private fun findMatchingEntry(context: Context, foldingFeature: androidx.window.layout.FoldingFeature? = null): CustomSdpEntry? {
        val configuration = context.resources.configuration
        val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)

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

    private fun resolve(context: Context, qualifier: DpQualifier, foldingFeature: androidx.window.layout.FoldingFeature?): Float {
        val foundEntry = findMatchingEntry(context, foldingFeature)
        val valueToUse = foundEntry?.customValue ?: initialBaseValue
        val actualQualifier = foundEntry?.finalQualifierResolver ?: qualifier
        return DimenSdp.getDimensionInPx(context, actualQualifier, valueToUse, foundEntry?.inverter ?: Inverter.DEFAULT)
    }

    private fun resolveRes(context: Context, qualifier: DpQualifier, foldingFeature: androidx.window.layout.FoldingFeature?): Int {
        val foundEntry = findMatchingEntry(context, foldingFeature)
        val valueToUse = foundEntry?.customValue ?: initialBaseValue
        val actualQualifier = foundEntry?.finalQualifierResolver ?: qualifier
        return DimenSdp.getResourceId(context, actualQualifier, valueToUse, foundEntry?.inverter ?: Inverter.DEFAULT)
    }

    /**
     * EN Final dimension value resolved in pixels.
     * PT Valor da dimensão final resolvida em pixels.
     *
     * @param context Application context
     * @param foldingFeature Optional Jetpack WindowManager FoldingFeature to accurately detect foldable states.
     */
    @JvmOverloads
    fun sdp(context: Context, foldingFeature: androidx.window.layout.FoldingFeature? = null): Float = resolve(context, DpQualifier.SMALL_WIDTH, foldingFeature)
    
    /**
     * EN Final dimension value resolved in pixels.
     * PT Valor da dimensão final resolvida em pixels.
     *
     * @param context Application context
     * @param foldingFeature Optional Jetpack WindowManager FoldingFeature to accurately detect foldable states.
     */
    @JvmOverloads
    fun hdp(context: Context, foldingFeature: androidx.window.layout.FoldingFeature? = null): Float = resolve(context, DpQualifier.HEIGHT, foldingFeature)
    
    /**
     * EN Final dimension value resolved in pixels.
     * PT Valor da dimensão final resolvida em pixels.
     *
     * @param context Application context
     * @param foldingFeature Optional Jetpack WindowManager FoldingFeature to accurately detect foldable states.
     */
    @JvmOverloads
    fun wdp(context: Context, foldingFeature: androidx.window.layout.FoldingFeature? = null): Float = resolve(context, DpQualifier.WIDTH, foldingFeature)
    
    /**
     * EN Final dimension value resolved as resource ID.
     * PT Valor da dimensão final resolvida como ID do recurso.
     *
     * @param context Application context
     * @param foldingFeature Optional Jetpack WindowManager FoldingFeature to accurately detect foldable states.
     */
    @JvmOverloads
    fun sdpRes(context: Context, foldingFeature: androidx.window.layout.FoldingFeature? = null): Int = resolveRes(context, DpQualifier.SMALL_WIDTH, foldingFeature)
    
    /**
     * EN Final dimension value resolved as resource ID.
     * PT Valor da dimensão final resolvida como ID do recurso.
     *
     * @param context Application context
     * @param foldingFeature Optional Jetpack WindowManager FoldingFeature to accurately detect foldable states.
     */
    @JvmOverloads
    fun hdpRes(context: Context, foldingFeature: androidx.window.layout.FoldingFeature? = null): Int = resolveRes(context, DpQualifier.HEIGHT, foldingFeature)
    
    /**
     * EN Final dimension value resolved as resource ID.
     * PT Valor da dimensão final resolvida como ID do recurso.
     *
     * @param context Application context
     * @param foldingFeature Optional Jetpack WindowManager FoldingFeature to accurately detect foldable states.
     */
    @JvmOverloads
    fun wdpRes(context: Context, foldingFeature: androidx.window.layout.FoldingFeature? = null): Int = resolveRes(context, DpQualifier.WIDTH, foldingFeature)
}
