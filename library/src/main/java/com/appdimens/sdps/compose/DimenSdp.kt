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

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.sdps.common.DpQualifier
import com.appdimens.sdps.common.Inverter

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
 * Extension for Pixel (Float) with dynamic scaling based on the **Smallest Width (swDP)**.
 *
 * PT
 * Extensão para Pixel (Float) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 */
@get:Composable
val Int.sdpPx: Float get() = LocalDensity.current.run { sdp.toPx() }

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
 * Pixel version of sdpPh.
 *
 * PT
 * Versão em pixel de sdpPh.
 */
@get:Composable
val Int.sdpPhPx: Float get() = LocalDensity.current.run { sdpPh.toPx() }

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
 * Pixel version of sdpLh.
 *
 * PT
 * Versão em pixel de sdpLh.
 */
@get:Composable
val Int.sdpLhPx: Float get() = LocalDensity.current.run { sdpLh.toPx() }

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
 * Pixel version of sdpPw.
 *
 * PT
 * Versão em pixel de sdpPw.
 */
@get:Composable
val Int.sdpPwPx: Float get() = LocalDensity.current.run { sdpPw.toPx() }

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
 * Pixel version of sdpLw.
 *
 * PT
 * Versão em pixel de sdpLw.
 */
@get:Composable
val Int.sdpLwPx: Float get() = LocalDensity.current.run { sdpLw.toPx() }

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
 * Extension for Pixel (Float) with dynamic scaling based on the **Screen Height (hDP)**.
 *
 * PT
 * Extensão para Pixel (Float) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 */
@get:Composable
val Int.hdpPx: Float get() = LocalDensity.current.run { hdp.toPx() }

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
 * Pixel version of hdpLw.
 *
 * PT
 * Versão em pixel de hdpLw.
 */
@get:Composable
val Int.hdpLwPx: Float get() = LocalDensity.current.run { hdpLw.toPx() }

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
 * Pixel version of hdpPw.
 *
 * PT
 * Versão em pixel de hdpPw.
 */
@get:Composable
val Int.hdpPwPx: Float get() = LocalDensity.current.run { hdpPw.toPx() }

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
 * Extension for Pixel (Float) with dynamic scaling based on the **Screen Width (wDP)**.
 *
 * PT
 * Extensão para Pixel (Float) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 */
@get:Composable
val Int.wdpPx: Float get() = LocalDensity.current.run { wdp.toPx() }

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
 * Pixel version of wdpLh.
 *
 * PT
 * Versão em pixel de wdpLh.
 */
@get:Composable
val Int.wdpLhPx: Float get() = LocalDensity.current.run { wdpLh.toPx() }

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

/**
 * EN
 * Pixel version of wdpPh.
 *
 * PT
 * Versão em pixel de wdpPh.
 */
@get:Composable
val Int.wdpPhPx: Float get() = LocalDensity.current.run { wdpPh.toPx() }

// -------------------------------------------------------------------------
// Aspect-ratio-aware Compose Dp (`sdpa` matrix; `sdpia` == `sdpa` API alias).
// -------------------------------------------------------------------------

/** EN Smallest Width + aspect ratio (appdimens-dynamic sdpa parity). PT Largura mínima + aspect ratio (`sdpa`). */
@get:Composable
val Int.sdpa: Dp get() = toDynamicScaledDp(DpQualifier.SMALL_WIDTH, applyAspectRatio = true)

@get:Composable
val Int.sdpPxa: Float get() = LocalDensity.current.run { sdpa.toPx() }

/** EN Multi-window suppression API alias (dynamic); identical to [sdpa] here. PT Alias ignorar multi‑janelas; igual a [sdpa]. */
@get:Composable
val Int.sdpia: Dp get() = sdpa

@get:Composable
val Int.sdpPxIa: Float get() = sdpPxa

@get:Composable
val Int.sdpPha: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, applyAspectRatio = true)

@get:Composable
val Int.sdpPxPha: Float get() = LocalDensity.current.run { sdpPha.toPx() }

@get:Composable
val Int.sdpPhia: Dp get() = sdpPha

@get:Composable
val Int.sdpPxPhia: Float get() = sdpPxPha

@get:Composable
val Int.sdpLha: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, applyAspectRatio = true)

@get:Composable
val Int.sdpPxLha: Float get() = LocalDensity.current.run { sdpLha.toPx() }

@get:Composable
val Int.sdpLhia: Dp get() = sdpLha

@get:Composable
val Int.sdpPxLhia: Float get() = sdpPxLha

@get:Composable
val Int.sdpPwa: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, applyAspectRatio = true)

@get:Composable
val Int.sdpPxPwa: Float get() = LocalDensity.current.run { sdpPwa.toPx() }

@get:Composable
val Int.sdpPwia: Dp get() = sdpPwa

@get:Composable
val Int.sdpPxPwia: Float get() = sdpPxPwa

@get:Composable
val Int.sdpLwa: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, applyAspectRatio = true)

@get:Composable
val Int.sdpPxLwa: Float get() = LocalDensity.current.run { sdpLwa.toPx() }

@get:Composable
val Int.sdpLwia: Dp get() = sdpLwa

@get:Composable
val Int.sdpPxLwia: Float get() = sdpPxLwa

@get:Composable
val Int.hdpa: Dp get() = toDynamicScaledDp(DpQualifier.HEIGHT, applyAspectRatio = true)

@get:Composable
val Int.hdpPxA: Float get() = LocalDensity.current.run { hdpa.toPx() }

@get:Composable
val Int.hdpia: Dp get() = hdpa

@get:Composable
val Int.hdpPxIa: Float get() = hdpPxA

@get:Composable
val Int.hdpLwa: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, applyAspectRatio = true)

@get:Composable
val Int.hdpPxLwa: Float get() = LocalDensity.current.run { hdpLwa.toPx() }

@get:Composable
val Int.hdpLwia: Dp get() = hdpLwa

@get:Composable
val Int.hdpPxLwia: Float get() = hdpPxLwa

@get:Composable
val Int.hdpPwa: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, applyAspectRatio = true)

@get:Composable
val Int.hdpPxPwa: Float get() = LocalDensity.current.run { hdpPwa.toPx() }

@get:Composable
val Int.hdpPwia: Dp get() = hdpPwa

@get:Composable
val Int.hdpPxPwia: Float get() = hdpPxPwa

@get:Composable
val Int.wdpa: Dp get() = toDynamicScaledDp(DpQualifier.WIDTH, applyAspectRatio = true)

@get:Composable
val Int.wdpPxA: Float get() = LocalDensity.current.run { wdpa.toPx() }

@get:Composable
val Int.wdpia: Dp get() = wdpa

@get:Composable
val Int.wdpPxIa: Float get() = wdpPxA

@get:Composable
val Int.wdpLha: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, applyAspectRatio = true)

@get:Composable
val Int.wdpPxLha: Float get() = LocalDensity.current.run { wdpLha.toPx() }

@get:Composable
val Int.wdpLhia: Dp get() = wdpLha

@get:Composable
val Int.wdpPxLhia: Float get() = wdpPxLha

@get:Composable
val Int.wdpPha: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, applyAspectRatio = true)

@get:Composable
val Int.wdpPxPha: Float get() = LocalDensity.current.run { wdpPha.toPx() }

@get:Composable
val Int.wdpPhia: Dp get() = wdpPha

@get:Composable
val Int.wdpPxPhia: Float get() = wdpPxPha

// EN Dynamic scaling functions (Resource-based).
// PT Funções de dimensionamento dinâmico (baseadas em recursos).

/**
 * EN
 * Converts an Int (the base Dp value) into a dynamically scaled Dp.
 * Loads the matching `@dimen` resource (`_Nsdp` / `_Nhdp` / `_Nwdp`).
 * If the resource is missing, returns the unscaled Compose `Int.dp` value.
 * When [applyAspectRatio] is true, multiplies by the library aspect-ratio adjustment.
 *
 * PT
 * Converte um Int (valor Dp base) em um Dp dinamicamente escalado.
 * Carrega o recurso `@dimen` correspondente (`_Nsdp` / `_Nhdp` / `_Nwdp`).
 * Se o recurso não existir, retorna o `Int.dp` padrão do Compose.
 * Com [applyAspectRatio], aplica o ajuste de aspect ratio da biblioteca.
 *
 * @param qualifier Screen axis used to select the resource name (s, h, w).
 * @param inverter Optional orientation-based axis switch.
 * @param applyAspectRatio Whether to apply the aspect-ratio adjustment (`*a` APIs).
 */
@Composable
fun Int.toDynamicScaledDp(
    qualifier: DpQualifier,
    inverter: Inverter = Inverter.DEFAULT,
    applyAspectRatio: Boolean = false,
): Dp {
    require(this in -300..600) {
        "Value must be between -300 and 600 to use the dynamic scaling dimension logic. Current value: $this"
    }

    val actualQualifier = rememberEffectiveQualifier(qualifier, inverter)
    val dimenResourceId = rememberDimenResourceId(actualQualifier, this)

    if (dimenResourceId == 0) return this.dp

    val baseDp = dimensionResource(id = dimenResourceId)
    if (!applyAspectRatio) return baseDp

    val adjustment = rememberAspectRatioAdjustment(actualQualifier)
    return if (adjustment == 1f) baseDp else Dp(baseDp.value * adjustment)
}
