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
package com.appdimens.sdps.compose

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.sdps.common.DpQualifierEntry
import com.appdimens.sdps.common.UiModeType
import com.appdimens.sdps.common.DpQualifier
import com.appdimens.sdps.common.Inverter
import com.appdimens.sdps.common.Orientation
import kotlin.math.abs

/**
 * EN
 * Represents a custom dimension entry with qualifiers and priority.
 * Used by the Scaled class to define specific values for screen conditions.
 *
 * PT
 * Representa uma entrada de dimensão customizada com qualificadores e prioridade.
 * Usada pela classe Scaled para definir valores específicos para condições de tela.
 *
 * @param uiModeType The UI mode (CAR, TELEVISION, WATCH, NORMAL). Null for any mode.
 * @param dpQualifierEntry The Dp qualifier entry (type and value, e.g., SMALL_WIDTH > 600). Null if only UI mode is used.
 * @param orientation The screen orientation (LANDSCAPE, PORTRAIT, DEFAULT).
 * @param customValue The Dp value to be used if the condition is met.
 * @param finalQualifierResolver Optional dimension qualifier (e.g., HEIGHT) to be applied at resolution time.
 * @param priority The resolution priority. 1 is more specific (UI + Qualifier), 3 is less specific (Qualifier only).
 * @param inverter The inverter type to adapt scaling width/height on rotation changes (default is Inverter.DEFAULT).
 */
data class CustomDpEntry(
    val uiModeType: UiModeType? = null,
    val dpQualifierEntry: DpQualifierEntry? = null,
    val orientation: Orientation? = Orientation.DEFAULT,
    val customValue: Dp,
    val finalQualifierResolver: DpQualifier? = null,
    val priority: Int,
    val inverter: Inverter? = Inverter.DEFAULT
)

/**
 * EN
 * Gets the actual value from the Configuration for the given DpQualifier.
 *
 * PT
 * Obtém o valor real da configuração (Configuration) para o DpQualifier dado.
 *
 * @param qualifier The type of qualifier (SMALL_WIDTH, HEIGHT, WIDTH).
 * @param configuration The current resource configuration.
 * @return The numeric value (in Dp) of the screen metric.
 */
private fun getQualifierValue(qualifier: DpQualifier, configuration: Configuration): Float {
    return when (qualifier) {
        DpQualifier.SMALL_WIDTH -> configuration.smallestScreenWidthDp.toFloat()
        DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
        DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
    }
}

/**
 * EN
 * Maps the uiMode value from the configuration to the library's UiModeType enum.
 *
 * PT
 * Mapeia o valor de uiMode da configuração para o enum UiModeType da biblioteca.
 */
fun fromConfiguration(uiMode: Int): UiModeType {
    // EN Uses a bitwise mask to isolate the UI mode type.
    // PT Usa uma máscara bitwise para isolar o tipo de modo de UI.
    return when (uiMode and Configuration.UI_MODE_TYPE_MASK) {
        Configuration.UI_MODE_TYPE_CAR -> UiModeType.CAR
        Configuration.UI_MODE_TYPE_TELEVISION -> UiModeType.TELEVISION
        Configuration.UI_MODE_TYPE_WATCH -> UiModeType.WATCH
        else -> UiModeType.NORMAL
    }
}

// EN Composable extensions for quick dynamic scaling.
// PT Extensões Composable para dimensionamento dinâmico rápido.

/**
 * EN
 * Extension for Dp with dynamic scaling based on the **Smallest Width (swDP)**.
 * Usage example: `16.sdp`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Exemplo de uso: `16.sdp`.
 */
@get:Composable
val Int.sdp: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH)

/**
 * EN
 * Extension for Dp with dynamic scaling based on the **Screen Height (hDP)**.
 * Usage example: `32.hdp`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Exemplo de uso: `32.hdp`.
 */
@get:Composable
val Int.hdp: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT)

/**
 * EN
 * Extension for Dp with dynamic scaling based on the **Screen Height (hDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hdp_lw`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**, mas
 * na orientação paisagem atua como **Largura da Tela (wDP)**.
 * Exemplo de uso: `32.hdp_lw`.
 */
@get:Composable
val Int.hdp_lw: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW)

/**
 * EN
 * Extension for Dp with dynamic scaling based on the **Screen Height (hDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hdp_pw`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**, mas
 * na orientação retrato atua como **Largura da Tela (wDP)**.
 * Exemplo de uso: `32.hdp_pw`.
 */
@get:Composable
val Int.hdp_pw: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW)

/**
 * EN
 * Extension for Dp with dynamic scaling based on the **Screen Width (wDP)**.
 * Usage example: `100.wdp`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Exemplo de uso: `100.wdp`.
 */
@get:Composable
val Int.wdp: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH)

/**
 * EN
 * Extension for Dp with dynamic scaling based on the **Screen Width (wDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wdp_lh`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**, mas
 * na orientação paisagem atua como **Altura da Tela (hDP)**.
 * Exemplo de uso: `100.wdp_lh`.
 */
@get:Composable
val Int.wdp_lh: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.PW_TO_LH)

/**
 * EN
 * Extension for Dp with dynamic scaling based on the **Screen Width (wDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wdp_ph`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**, mas
 * na orientação retrato atua como **Altura da Tela (hDP)**.
 * Exemplo de uso: `100.wdp_ph`.
 */
@get:Composable
val Int.wdp_ph: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.LW_TO_PH)

// EN Methods for creating the Scaled class.
// PT Métodos de criação da classe Scaled.

/**
 * EN
 * Starts the build chain for the custom dimension Scaled from a base Dp.
 * Usage example: `100.dp.scaled().screen(...)`.
 *
 * PT
 * Inicia a cadeia de construção para a dimensão customizada Scaled a partir de um Dp base.
 * Exemplo de uso: `100.dp.scaled().screen(...)`.
 */
@Composable
fun Dp.scaledDp(): Scaled = Scaled(this@scaledDp)

/**
 * EN
 * Starts the build chain for the custom dimension Scaled from a base Int.
 * Usage example: `100.scaled().screen(...)`.
 *
 * PT
 * Inicia a cadeia de construção para a dimensão customizada Scaled a partir de um Int base.
 * Exemplo de uso: `100.scaled().screen(...)`.
 */
@Composable
fun Int.scaledDp(): Scaled = this.dp.scaledDp()

// EN Main class for conditional scaling.
// PT Classe principal de dimensionamento condicional.

/**
 * EN
 * A Stable class that allows defining custom dimensions
 * based on screen qualifiers (UiModeType, Width, Height, Smallest Width).
 *
 * The Dp value is resolved at composition (Compose) and uses the base value or a
 * custom value, applying dynamic scaling at the end.
 *
 * PT
 * Classe Stable que permite a definição de dimensões customizadas
 * baseadas em qualificadores de tela (UiModeType, Largura, Altura, Smallest Width).
 *
 * O valor Dp é resolvido na composição (Compose) e usa o valor base ou um
 * valor customizado, aplicando o dimensionamento dinâmico no final.
 */
@Stable
class Scaled private constructor(
    private val initialBaseDp: Dp,
    // EN Custom entries are always kept sorted by priority.
    // PT As entradas customizadas são sempre mantidas ordenadas por prioridade.
    private val sortedCustomEntries: List<CustomDpEntry> = emptyList()
) {

    // EN Main constructor to start the chain.
    // PT Construtor principal para iniciar a cadeia.
    constructor(initialBaseDp: Dp) : this(initialBaseDp, emptyList())

    /**
     * EN
     * Adds a new entry and re-sorts the list.
     * Sorting is crucial: first by priority (ascending),
     * and then by dpQualifierEntry.value (descending) so that larger qualifiers
     * (e.g., sw600dp) are checked before smaller qualifiers (e.g., sw320dp).
     *
     * PT
     * Adiciona uma nova entrada e reordena a lista.
     * A ordenação é crucial: primeiro por priority (crescente),
     * e depois por dpQualifierEntry.value (decrescente) para que qualificadores
     * maiores (e.g., sw600dp) sejam verificados antes de qualificadores menores (e.g., sw320dp).
     */
    private fun reorderEntries(newEntry: CustomDpEntry): List<CustomDpEntry> {
        return (sortedCustomEntries + newEntry).sortedWith(
            compareBy<CustomDpEntry> { it.priority } // EN Priority 1 (most specific) first. / PT Prioridade 1 (mais específico) primeiro.
                .thenByDescending { it.dpQualifierEntry?.value ?: 0 } // EN Higher value qualifier first. / PT Qualificador de valor maior primeiro.
        )
    }

    // EN Fluent methods for construction (Chain of Responsibility).
    // PT Métodos fluentes para construção (Chain of Responsibility).

    /**
     * EN
     * Priority 1: Most specific qualifier - Combines UiModeType AND Dp Qualifier (sw, h, w).
     *
     * PT
     * Prioridade 1: Qualificador mais específico - Combina UiModeType E Qualificador de Dp (sw, h, w).
     */
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Int,
        orientation: Orientation? = Orientation.DEFAULT,
        customValue: Dp,
        finalQualifierResolver: DpQualifier? = null,
        inverter: Inverter? = Inverter.DEFAULT
    ): Scaled {
        val entry = CustomDpEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 1,
            inverter = inverter
        )
        return Scaled(initialBaseDp, reorderEntries(entry))
    }

    /**
     * EN
     * Priority 1: Most specific qualifier - Combines UiModeType AND Dp Qualifier (sw, h, w).
     * This is an overload that accepts an Int for `customValue`.
     *
     * PT
     * Prioridade 1: Qualificador mais específico - Combina UiModeType E Qualificador de Dp (sw, h, w).
     * Esta é uma sobrecarga que aceita um Int para `customValue`.
     */
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Int,
        customValue: Int,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation? = Orientation.DEFAULT,
        inverter: Inverter? = Inverter.DEFAULT
    ): Scaled {
        val entry = CustomDpEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue),
            orientation = orientation,
            customValue = customValue.dp,
            finalQualifierResolver = finalQualifierResolver,
            priority = 1,
            inverter = inverter
        )
        return Scaled(initialBaseDp, reorderEntries(entry))
    }

    /**
     * EN
     * Priority 2: UiModeType qualifier (e.g., TELEVISION, WATCH).
     *
     * PT
     * Prioridade 2: Qualificador de UiModeType (e.g., TELEVISION, WATCH).
     */
    fun screen(type: UiModeType,
               customValue: Dp,
               finalQualifierResolver: DpQualifier? = null,
               orientation: Orientation? = Orientation.DEFAULT,
               inverter: Inverter? = Inverter.DEFAULT
    ): Scaled {
        val entry = CustomDpEntry(
            uiModeType = type,
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 2,
            inverter = inverter
        )
        return Scaled(initialBaseDp, reorderEntries(entry))
    }

    /**
     * EN
     * Priority 2: UiModeType qualifier (e.g., TELEVISION, WATCH).
     * This is an overload that accepts an Int for `customValue`.
     *
     * PT
     * Prioridade 2: Qualificador de UiModeType (e.g., TELEVISION, WATCH).
     * Esta é uma sobrecarga que aceita um Int para `customValue`.
     */
    fun screen(type: UiModeType,
               customValue: Int,
               finalQualifierResolver: DpQualifier? = null,
               orientation: Orientation? = Orientation.DEFAULT,
               inverter: Inverter? = Inverter.DEFAULT
    ): Scaled {
        val entry = CustomDpEntry(
            uiModeType = type,
            orientation = orientation,
            customValue = customValue.dp,
            finalQualifierResolver = finalQualifierResolver,
            priority = 2,
            inverter = inverter
        )
        return Scaled(initialBaseDp, reorderEntries(entry))
    }

    /**
     * EN
     * Priority 3: Dp qualifier (sw, h, w) without UiModeType restriction.
     *
     * PT
     * Prioridade 3: Qualificador de Dp (sw, h, w) sem restrição de UiModeType.
     */
    fun screen(type: DpQualifier,
               value: Int,
               customValue: Dp,
               finalQualifierResolver: DpQualifier? = null,
               orientation: Orientation? = Orientation.DEFAULT,
               inverter: Inverter? = Inverter.DEFAULT
    ): Scaled {
        val entry = CustomDpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 3,
            inverter = inverter
        )
        return Scaled(initialBaseDp, reorderEntries(entry))
    }

    /**
     * EN
     * Priority 3: Dp qualifier (sw, h, w) without UiModeType restriction.
     * This is an overload that accepts an Int for `customValue`.
     *
     * PT
     * Prioridade 3: Qualificador de Dp (sw, h, w) sem restrição de UiModeType.
     * Esta é uma sobrecarga que aceita um Int para `customValue`.
     */
    fun screen(type: DpQualifier,
               value: Int,
               customValue: Int,
               finalQualifierResolver: DpQualifier? = null,
               orientation: Orientation? = Orientation.DEFAULT,
               inverter: Inverter? = Inverter.DEFAULT): Scaled {
        val entry = CustomDpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            orientation = orientation,
            customValue = customValue.dp,
            finalQualifierResolver = finalQualifierResolver,
            priority = 3,
            inverter = inverter
        )
        return Scaled(initialBaseDp, reorderEntries(entry))
    }

    /**
     * EN
     * Priority 4: Orientation.
     * This is an overload that accepts an Int for `customValue`.
     *
     * PT
     * Prioridade 4: Orientation.
     * Esta é uma sobrecarga que aceita um Int para `customValue`.
     */
    fun screen(orientation: Orientation = Orientation.DEFAULT,
               customValue: Int,
               finalQualifierResolver: DpQualifier? = null,
               inverter: Inverter? = Inverter.DEFAULT): Scaled {
        val entry = CustomDpEntry(
            orientation = orientation,
            customValue = customValue.dp,
            finalQualifierResolver = finalQualifierResolver,
            priority = 4,
            inverter = inverter
        )
        return Scaled(initialBaseDp, reorderEntries(entry))
    }

    // EN Composable resolution logic.
    // PT Lógica de resolução Composable.

    /**
     * EN
     * Resolves the final dimension. This is the Composable part that reads the current configuration
     * and decides which Dp to use.
     *
     * PT
     * Resolve a dimensão final. Esta é a parte Composable que lê a configuração atual
     * e decide qual Dp usar.
     *
     * @param qualifier The dynamic scaling qualifier that will be applied at the end
     * (SMALL_WIDTH, HEIGHT, or WIDTH), determined by the access property (.sdp, .hdp, .wdp).
     */
    @SuppressLint("ConfigurationScreenWidthHeight") // EN The annotation is necessary as we access screen metrics. / PT A anotação é necessária, pois acessamos métricas da tela.
    @Composable
    private fun resolve(qualifier: DpQualifier): Dp {
        val configuration = LocalConfiguration.current
        val currentUiModeType = fromConfiguration(configuration.uiMode)

        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        // EN Tries to find the first custom entry that qualifies.
        // The list is checked in the priority order defined in reorderEntries.
        // PT Tenta encontrar a primeira entrada customizada que se qualifica.
        // A lista é verificada na ordem de prioridade definida em reorderEntries.
        val foundEntry = sortedCustomEntries.firstOrNull { entry ->
            val qualifierEntry = entry.dpQualifierEntry
            val uiModeMatch = entry.uiModeType == null || entry.uiModeType == currentUiModeType
            
            // EN Checks if the entry orientation matches the actual screen orientation
            // PT Verifica se a orientação da entrada bate com a orientação atual da tela
            val orientationMatch = when (entry.orientation) {
                Orientation.LANDSCAPE -> isLandscape
                Orientation.PORTRAIT -> isPortrait
                else -> true
            }

            if (qualifierEntry != null) {
                // EN Checks if the screen value is GREATER THAN OR EQUAL to the qualifier value.
                // PT Verifica se o valor da tela é MAIOR OU IGUAL ao valor do qualificador.
                val qualifierMatch = getQualifierValue(qualifierEntry.type, configuration) >= qualifierEntry.value

                // EN Priority 1: Must match uiModeMatch AND qualifierMatch AND orientationMatch.
                // PT Prioridade 1: Deve casar uiModeMatch E qualifierMatch E orientationMatch.
                if (entry.priority == 1 && uiModeMatch && qualifierMatch && orientationMatch) return@firstOrNull true

                // EN Priority 3: Must match only qualifierMatch AND orientationMatch.
                // PT Prioridade 3: Deve casar apenas qualifierMatch E orientationMatch.
                if (entry.priority == 3 && qualifierMatch && orientationMatch) return@firstOrNull true

                return@firstOrNull false // EN Did not match P1 or P3. / PT Não casou com P1 ou P3.
            } else {
                // EN Priority 2: Must match only uiModeMatch AND orientationMatch (without Dp qualifier).
                // PT Prioridade 2: Deve casar apenas uiModeMatch E orientationMatch (sem qualificador de Dp).
                if (entry.priority == 2 && uiModeMatch && orientationMatch) return@firstOrNull true

                // EN Priority 4: Must match only orientationMatch (without Dp qualifier).
                // PT Prioridade 4: Deve casar apenas orientationMatch (sem qualificador de Dp).
                if (entry.priority == 4 && orientationMatch) return@firstOrNull true

                return@firstOrNull false // EN Did not match P2 or P4.
            }
        }

        // EN Determines the Dp value to be used: custom or the initial base.
        // PT Determina o valor de Dp a ser usado: customizado ou o base inicial.
        val dpToUse: Dp = foundEntry?.customValue ?: initialBaseDp

        // EN Applies dynamic scaling to the base/custom value,
        // using the 'qualifier' passed explicitly by the access property
        // or the specific 'finalQualifierResolver' from the custom entry if defined.
        // PT Aplica o dimensionamento dinâmico ao valor base/customizado,
        // usando o 'qualifier' passado explicitamente pela propriedade de acesso
        // ou o 'finalQualifierResolver' específico da entrada customizada, se definido.
        val finalQualifier = foundEntry?.finalQualifierResolver ?: qualifier

        val baseIntDp = dpToUse.value.toInt()
        return baseIntDp.toDynamicScaledDp(finalQualifier, foundEntry?.inverter ?: Inverter.DEFAULT)
        //return dpToUse.toDynamicScaledDp(qualifier, foundEntry?.inverter)
    }

    /**
     * EN
     * The final Dp value that is resolved in Compose.
     *
     * PT
     * O valor final Dp que é resolvido no Compose.
     */
    @get:Composable
    val sdp: Dp get() = resolve(DpQualifier.SMALL_WIDTH)
    /**
     * EN
     * The final Dp value that is resolved in Compose.
     *
     * PT
     * O valor final Dp que é resolvido no Compose.
     */
    @get:Composable
    val hdp: Dp get() = resolve(DpQualifier.HEIGHT)
    /**
     * EN
     * The final Dp value that is resolved in Compose.
     *
     * PT
     * O valor final Dp que é resolvido no Compose.
     */
    @get:Composable
    val wdp: Dp get() = resolve(DpQualifier.WIDTH)
}

// EN Dynamic scaling functions (Resource-based).
// PT Funções de dimensionamento dinâmico (baseadas em recursos).

/**
 * EN
 * Finds the dimension resource ID (`dimen`) by the constructed name.
 * The SuppressLint annotation is used because getIdentifier is discouraged,
 * but it is necessary for this type of dynamic logic based on naming convention.
 *
 * PT
 * Encontra o ID de recurso de dimensão (`dimen`) pelo nome construído.
 * A anotação SuppressLint é usada porque getIdentifier é desencorajada,
 * mas é necessária para este tipo de lógica dinâmica baseada em convenção de nomenclatura.
 *
 * @param resourceName The expected name of the resource, e.g., `_s16dp`.
 * @return The resource ID or 0 (or -1) if not found.
 */
@SuppressLint("LocalContextResourcesRead", "DiscouragedApi")
@Composable
private fun findResourceIdByName(resourceName: String): Int {
    val context = LocalContext.current
    return context.resources.getIdentifier(
        resourceName,
        "dimen", // EN The resource type is 'dimen'. / PT O tipo de recurso é 'dimen'.
        context.packageName
    )
}

/**
 * EN
 * Converts an Int (the base Dp value) into a dynamically scaled Dp.
 * The logic tries to find a corresponding dimension resource in the 'res/values/' folder.
 * 1. Constructs the resource name based on the value (this) and the qualifier (qualifier).
 * 2. Tries to load the resource via dimensionResource.
 * 3. If the resource is found (e.g., in `values-sw600dp/dimens.xml`), that value is used.
 * 4. If the resource is not found, the original value is used as a Dp (the default Compose Int.dp).
 *
 * PT
 * Converte um Int (o valor base de Dp) em um Dp dinamicamente escalado.
 * A lógica tenta encontrar um recurso de dimensão correspondente na pasta 'res/values/'.
 * 1. Constrói o nome do recurso baseado no valor (this) e no qualificador (qualifier).
 * 2. Tenta carregar o recurso via dimensionResource.
 * 3. Se o recurso for encontrado (e.g., em `values-sw600dp/dimens.xml`), esse valor é usado.
 * 4. Se o recurso não for encontrado, o valor original é usado como Dp (o Int.dp padrão do Compose).
 *
 * @param qualifier The screen qualifier used to construct the resource name (s, h, w).
 * @return The Dp value loaded from the resource or the base Dp value.
 */
@Composable
fun Int.toDynamicScaledDp(qualifier: DpQualifier, inverter: Inverter = Inverter.DEFAULT): Dp {
    // EN Validation requirement (limits usage to avoid creating thousands of dimens files).
    // PT Requisito de validação (limita o uso para evitar a criação de milhares de arquivos dimens).
    require(this in -300..600) { "Value must be between -300 and 600 to use the dynamic scaling dimension logic. Current value:: $this" }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    var actualQualifier = qualifier

    when (inverter) {
        Inverter.PH_TO_LW -> if (isLandscape && qualifier == DpQualifier.HEIGHT) actualQualifier = DpQualifier.WIDTH
        Inverter.PW_TO_LH -> if (isLandscape && qualifier == DpQualifier.WIDTH) actualQualifier = DpQualifier.HEIGHT
        Inverter.LH_TO_PW -> if (isPortrait && qualifier == DpQualifier.HEIGHT) actualQualifier = DpQualifier.WIDTH
        Inverter.LW_TO_PH -> if (isPortrait && qualifier == DpQualifier.WIDTH) actualQualifier = DpQualifier.HEIGHT
        Inverter.DEFAULT -> {}
    }

    // EN Determines the qualifier prefix: s (Smallest Width), h (Height), w (Width).
    // PT Determina o prefixo do qualificador: s (Smallest Width), h (Height), w (Width).
    val attrName = when (actualQualifier) {
        DpQualifier.HEIGHT -> "h"
        DpQualifier.WIDTH -> "w"
        else -> "s"
    }

    // EN Handles negative values, using the "minus" prefix in the naming convention.
    // PT Lida com valores negativos, usando o prefixo "minus" na convenção de nome.
    val prefix = if (this < 0) "minus" else ""
    // EN Constructs the resource name, e.g., "_s16dp", "_minuss16dp", "_w100dp".
    // PT Constrói o nome do recurso, e.g., "_s16dp", "_minuss16dp", "_w100dp".
    val resourceName = "_${prefix}${abs(this)}${attrName}dp"

    val dimenResourceId = findResourceIdByName(resourceName)

    // EN If the resource ID is valid (not 0 or -1), loads the dimension.
    // PT Se o ID do recurso for válido (diferente de 0 ou -1), carrega a dimensão.
    return if (dimenResourceId != 0 && dimenResourceId != -1)
        dimensionResource(id = dimenResourceId)
    else this.dp // EN Otherwise, returns the default Compose Dp value. / PT Caso contrário, retorna o valor de Dp padrão do Compose.
}
