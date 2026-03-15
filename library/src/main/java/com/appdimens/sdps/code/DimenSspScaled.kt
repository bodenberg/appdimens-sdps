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
 * EN Starts the build chain for ScaledSp from a base Int.
 * Usage example: `16.scaledSp().screen(...)`.
 *
 * PT Inicia a cadeia de construção para ScaledSp a partir de um Int base.
 * Exemplo de uso: `16.scaledSp().screen(...)`.
 */
fun Int.scaledSp(): ScaledSp = ScaledSp(this)

/**
 * EN
 * Represents a custom Sp entry with qualifiers and priority for code.
 *
 * PT
 * Representa uma entrada de Sp customizada com qualificadores e prioridade para código.
 */
data class CustomSpEntry(
    val uiModeType: UiModeType? = null,
    val dpQualifierEntry: DpQualifierEntry? = null,
    val orientation: Orientation = Orientation.DEFAULT,
    val customValue: Int,
    val finalQualifierResolver: DpQualifier? = null,
    val priority: Int,
    val inverter: Inverter = Inverter.DEFAULT,
    val fontScale: Boolean = true
)

/**
 * EN
 * A builder class that allows defining custom Sp dimensions based on screen qualifiers (non-Compose).
 * Resolves to the final dimension in Sp pixels.
 *
 * PT
 * Classe builder que permite definir dimensões Sp customizadas baseadas em qualificadores de tela (não-Compose).
 * Resolve para a dimensão final em pixels Sp.
 */
class ScaledSp internal constructor(
    private val initialBaseValue: Int,
    private val defaultFontScale: Boolean = true,
    private val sortedCustomEntries: List<CustomSpEntry> = emptyList()
) {
    constructor(initialBaseValue: Int) : this(initialBaseValue, true, emptyList())

    private fun reorderEntries(newEntry: CustomSpEntry): List<CustomSpEntry> {
        return (sortedCustomEntries + newEntry).sortedWith(
            compareBy<CustomSpEntry> { it.priority }
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
        inverter: Inverter = Inverter.DEFAULT,
        fontScale: Boolean = defaultFontScale
    ): ScaledSp {
        val entry = CustomSpEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 1,
            inverter = inverter,
            fontScale = fontScale
        )
        return ScaledSp(initialBaseValue, defaultFontScale, reorderEntries(entry))
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
        inverter: Inverter = Inverter.DEFAULT,
        fontScale: Boolean = defaultFontScale
    ): ScaledSp {
        val entry = CustomSpEntry(
            uiModeType = type,
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 2,
            inverter = inverter,
            fontScale = fontScale
        )
        return ScaledSp(initialBaseValue, defaultFontScale, reorderEntries(entry))
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
        inverter: Inverter = Inverter.DEFAULT,
        fontScale: Boolean = defaultFontScale
    ): ScaledSp {
        val entry = CustomSpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 3,
            inverter = inverter,
            fontScale = fontScale
        )
        return ScaledSp(initialBaseValue, defaultFontScale, reorderEntries(entry))
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
        inverter: Inverter = Inverter.DEFAULT,
        fontScale: Boolean = defaultFontScale
    ): ScaledSp {
        val entry = CustomSpEntry(
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 4,
            inverter = inverter,
            fontScale = fontScale
        )
        return ScaledSp(initialBaseValue, defaultFontScale, reorderEntries(entry))
    }

    private fun findMatchingEntry(context: Context, foldingFeature: androidx.window.layout.FoldingFeature? = null): CustomSpEntry? {
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
                if (entry.priority == 2 && uiModeMatch && orientationMatch) return@firstOrNull true
                if (entry.priority == 4 && orientationMatch) return@firstOrNull true
                return@firstOrNull false
            }
        }
    }

    private fun resolve(context: Context, qualifier: DpQualifier, foldingFeature: androidx.window.layout.FoldingFeature?): Float {
        val foundEntry = findMatchingEntry(context, foldingFeature)
        val valueToUse = foundEntry?.customValue ?: initialBaseValue
        val actualQualifier = foundEntry?.finalQualifierResolver ?: qualifier
        val actualFontScale = foundEntry?.fontScale ?: defaultFontScale
        return DimenSsp.getDimensionInSpPx(context, actualQualifier, valueToUse, foundEntry?.inverter ?: Inverter.DEFAULT, actualFontScale)
    }

    private fun resolveRes(context: Context, qualifier: DpQualifier, foldingFeature: androidx.window.layout.FoldingFeature?): Int {
        val foundEntry = findMatchingEntry(context, foldingFeature)
        val valueToUse = foundEntry?.customValue ?: initialBaseValue
        val actualQualifier = foundEntry?.finalQualifierResolver ?: qualifier
        return DimenSsp.getResourceId(context, actualQualifier, valueToUse, foundEntry?.inverter ?: Inverter.DEFAULT)
    }

    /**
     * EN Final Sp pixels value resolved using Smallest Width.
     * PT Valor final em pixels Sp resolvido usando Smallest Width.
     */
    @JvmOverloads
    fun ssp(context: Context, foldingFeature: androidx.window.layout.FoldingFeature? = null): Float = resolve(context, DpQualifier.SMALL_WIDTH, foldingFeature)

    /**
     * EN Final Sp pixels value resolved using Screen Height.
     * PT Valor final em pixels Sp resolvido usando Altura da Tela.
     */
    @JvmOverloads
    fun hsp(context: Context, foldingFeature: androidx.window.layout.FoldingFeature? = null): Float = resolve(context, DpQualifier.HEIGHT, foldingFeature)

    /**
     * EN Final Sp pixels value resolved using Screen Width.
     * PT Valor final em pixels Sp resolvido usando Largura da Tela.
     */
    @JvmOverloads
    fun wsp(context: Context, foldingFeature: androidx.window.layout.FoldingFeature? = null): Float = resolve(context, DpQualifier.WIDTH, foldingFeature)

    /**
     * EN Final resource ID resolved using Smallest Width.
     * PT ID de recurso final resolvido usando Smallest Width.
     */
    @JvmOverloads
    fun sspRes(context: Context, foldingFeature: androidx.window.layout.FoldingFeature? = null): Int = resolveRes(context, DpQualifier.SMALL_WIDTH, foldingFeature)

    /**
     * EN Final resource ID resolved using Screen Height.
     * PT ID de recurso final resolvido usando Altura da Tela.
     */
    @JvmOverloads
    fun hspRes(context: Context, foldingFeature: androidx.window.layout.FoldingFeature? = null): Int = resolveRes(context, DpQualifier.HEIGHT, foldingFeature)

    /**
     * EN Final resource ID resolved using Screen Width.
     * PT ID de recurso final resolvido usando Largura da Tela.
     */
    @JvmOverloads
    fun wspRes(context: Context, foldingFeature: androidx.window.layout.FoldingFeature? = null): Int = resolveRes(context, DpQualifier.WIDTH, foldingFeature)
}
