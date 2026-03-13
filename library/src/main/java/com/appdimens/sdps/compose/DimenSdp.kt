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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.sdps.common.DpQualifier
import com.appdimens.sdps.common.Inverter
import kotlin.math.abs

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
internal fun getQualifierValue(qualifier: DpQualifier, configuration: Configuration): Float {
    return when (qualifier) {
        DpQualifier.SMALL_WIDTH -> configuration.smallestScreenWidthDp.toFloat()
        DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
        DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
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
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.sdpPh`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**, mas
 * na orientação retrato atua como **Altura da Tela (hDP)**.
 * Exemplo de uso: `32.sdpPh`.
 */
@get:Composable
val Int.sdpPh: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH)

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.sdpLh`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**, mas
 * na orientação paisagem atua como **Altura da Tela (hDP)**.
 * Exemplo de uso: `32.sdpLh`.
 */
@get:Composable
val Int.sdpLh: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH)

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.sdpPw`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**, mas
 * na orientação retrato atua como **Largura da Tela (wDP)**.
 * Exemplo de uso: `32.sdpPw`.
 */
@get:Composable
val Int.sdpPw: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW)

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.sdpLw`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**, mas
 * na orientação paisagem atua como **Largura da Tela (wDP)**.
 * Exemplo de uso: `32.sdpLw`.
 */
@get:Composable
val Int.sdpLw: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW)

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
 * Usage example: `32.hdpLw`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**, mas
 * na orientação paisagem atua como **Largura da Tela (wDP)**.
 * Exemplo de uso: `32.hdpLw`.
 */
@get:Composable
val Int.hdpLw: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW)

/**
 * EN
 * Extension for Dp with dynamic scaling based on the **Screen Height (hDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hdpPw`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**, mas
 * na orientação retrato atua como **Largura da Tela (wDP)**.
 * Exemplo de uso: `32.hdpPw`.
 */
@get:Composable
val Int.hdpPw: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW)

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
 * Usage example: `100.wdpLh`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**, mas
 * na orientação paisagem atua como **Altura da Tela (hDP)**.
 * Exemplo de uso: `100.wdpLh`.
 */
@get:Composable
val Int.wdpLh: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.PW_TO_LH)

/**
 * EN
 * Extension for Dp with dynamic scaling based on the **Screen Width (wDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wdpPh`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**, mas
 * na orientação retrato atua como **Altura da Tela (hDP)**.
 * Exemplo de uso: `100.wdpPh`.
 */
@get:Composable
val Int.wdpPh: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.LW_TO_PH)

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
        Inverter.SW_TO_LH -> if (isLandscape && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.HEIGHT
        Inverter.SW_TO_LW -> if (isLandscape && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.WIDTH
        Inverter.SW_TO_PH -> if (isPortrait && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.HEIGHT
        Inverter.SW_TO_PW -> if (isPortrait && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.WIDTH
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
