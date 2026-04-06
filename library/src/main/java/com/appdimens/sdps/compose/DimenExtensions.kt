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
import android.app.Activity
import android.content.ContextWrapper
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.appdimens.sdps.common.DpQualifier
import com.appdimens.sdps.common.Orientation
import com.appdimens.sdps.common.UiModeType

// EN Rotation facilitator extensions.
// PT Extensões facilitadoras para rotação.

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device is in the specified [orientation],
 * it uses [rotationValue] scaled with the given [finalQualifierResolver].
 * Usage example: `30.sdpRot(45, DpQualifier.SMALL_WIDTH, Orientation.LANDSCAPE)`
 * → 30.sdp by default, 45 scaled by SMALL_WIDTH in landscape.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo está na [orientation] especificada,
 * usa [rotationValue] escalado com o [finalQualifierResolver] dado.
 * Exemplo de uso: `30.sdpRot(45, DpQualifier.SMALL_WIDTH, Orientation.LANDSCAPE)`
 * → 30.sdp por padrão, 45 escalado por SMALL_WIDTH no paisagem.
 */
@Composable
fun Int.sdpRotate(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE): Dp {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicScaledDp(finalQualifierResolver)
    } else {
        this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH)
    }
}

/**
 * EN
 * Pixel version of sdpRotate (Int).
 *
 * PT
 * Versão em pixel de sdpRotate (Int).
 */
@Composable
fun Int.sdpRotatePx(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE): Float {
    return LocalDensity.current.run { sdpRotate(rotationValue, finalQualifierResolver, orientation).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun Dp.sdpRotate(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE): Dp {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicScaledDp(finalQualifierResolver)
    } else {
        this.value.toInt().toDynamicScaledDp(finalQualifierResolver)
    }
}

/**
 * EN
 * Pixel version of sdpRotate (Dp).
 *
 * PT
 * Versão em pixel de sdpRotate (Dp).
 */
@Composable
fun Dp.sdpRotatePx(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE): Float {
    return LocalDensity.current.run { sdpRotate(rotationValue, finalQualifierResolver, orientation).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun Dp.sdpRotatePlain(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE): Dp {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicScaledDp(finalQualifierResolver)
    } else {
        this
    }
}

/**
 * EN
 * Pixel version of sdpRotatePlain.
 *
 * PT
 * Versão em pixel de sdpRotatePlain.
 */
@Composable
fun Dp.sdpRotatePlainPx(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE): Float {
    return LocalDensity.current.run { sdpRotatePlain(rotationValue, finalQualifierResolver, orientation).toPx() }
}

/**
 * EN
 * Plain rotation override using **already-resolved** [Dp] values. No resource lookup and no
 * dynamic scaling on either side—only orientation branching. Prefer this when nesting facilitator
 * extensions or when both sizes were computed elsewhere (e.g. another extension).
 * For catalog-based scaling of the rotation value, use [sdpRotatePlain] with an [Int] instead.
 *
 * PT
 * Substituição por rotação **Plain** com valores [Dp] **já resolvidos**. Sem busca de recurso e
 * sem escalonamento dinâmico em nenhum dos lados—apenas o desvio por orientação. Recomendado ao
 * aninhar facilitadores ou quando ambos os tamanhos já foram calculados (por exemplo, por outra extensão).
 * Para escalar o valor de rotação a partir do catálogo, use [sdpRotatePlain] com [Int].
 *
 * Usage example / Exemplo: `30.sdp.sdpRotatePlain(20.sdp)` or `30.sdp.sdpRotatePlain(20.sdp, Orientation.LANDSCAPE)`.
 */
@Composable
fun Dp.sdpRotatePlain(rotationDp: Dp, orientation: Orientation = Orientation.LANDSCAPE): Dp {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) rotationDp else this
}

/**
 * EN
 * Pixel version of [sdpRotatePlain] with pre-resolved [Dp] values.
 *
 * PT
 * Versão em pixel de [sdpRotatePlain] com valores [Dp] já resolvidos.
 */
@Composable
fun Dp.sdpRotatePlainPx(rotationDp: Dp, orientation: Orientation = Orientation.LANDSCAPE): Float {
    return LocalDensity.current.run { sdpRotatePlain(rotationDp, orientation).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Uses the base value by default, but when the device is in the specified [orientation],
 * it uses [rotationValue] scaled with the given [finalQualifierResolver].
 * Usage example: `30.hdpRot(45, DpQualifier.HEIGHT, Orientation.LANDSCAPE)`
 * → 30.hdp by default, 45 scaled by HEIGHT in landscape.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo está na [orientation] especificada,
 * usa [rotationValue] escalado com o [finalQualifierResolver] dado.
 * Exemplo de uso: `30.hdpRot(45, DpQualifier.HEIGHT, Orientation.LANDSCAPE)`
 * → 30.hdp por padrão, 45 escalado por HEIGHT no paisagem.
 */
@Composable
fun Int.hdpRotate(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE): Dp {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicScaledDp(finalQualifierResolver)
    } else {
        this.toDynamicScaledDp(DpQualifier.HEIGHT)
    }
}

/**
 * EN
 * Pixel version of hdpRotate (Int).
 *
 * PT
 * Versão em pixel de hdpRotate (Int).
 */
@Composable
fun Int.hdpRotatePx(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE): Float {
    return LocalDensity.current.run { hdpRotate(rotationValue, finalQualifierResolver, orientation).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun Dp.hdpRotate(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE): Dp {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicScaledDp(finalQualifierResolver)
    } else {
        this.value.toInt().toDynamicScaledDp(finalQualifierResolver)
    }
}

/**
 * EN
 * Pixel version of hdpRotate (Dp).
 *
 * PT
 * Versão em pixel de hdpRotate (Dp).
 */
@Composable
fun Dp.hdpRotatePx(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE): Float {
    return LocalDensity.current.run { hdpRotate(rotationValue, finalQualifierResolver, orientation).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun Dp.hdpRotatePlain(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE): Dp {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicScaledDp(finalQualifierResolver)
    } else {
        this
    }
}

/**
 * EN
 * Pixel version of hdpRotatePlain.
 *
 * PT
 * Versão em pixel de hdpRotatePlain.
 */
@Composable
fun Dp.hdpRotatePlainPx(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE): Float {
    return LocalDensity.current.run { hdpRotatePlain(rotationValue, finalQualifierResolver, orientation).toPx() }
}

/**
 * EN
 * Plain rotation override for **hDP** semantics using **already-resolved** [Dp] values.
 * No resource lookup and no dynamic scaling—only orientation branching. Prefer when nesting
 * extensions or when both sizes are precomputed. For catalog-based rotation values, use
 * [hdpRotatePlain] with an [Int].
 *
 * PT
 * Substituição por rotação **Plain** (semântica **hDP**) com [Dp] **já resolvidos**.
 * Sem recurso nem escalonamento dinâmico—só o desvio por orientação. Recomendado ao aninhar
 * extensões ou quando ambos os valores já foram calculados. Para o valor de rotação via catálogo,
 * use [hdpRotatePlain] com [Int].
 */
@Composable
fun Dp.hdpRotatePlain(rotationDp: Dp, orientation: Orientation = Orientation.LANDSCAPE): Dp {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) rotationDp else this
}

/**
 * EN
 * Pixel version of [hdpRotatePlain] with pre-resolved [Dp] values.
 *
 * PT
 * Versão em pixel de [hdpRotatePlain] com valores [Dp] já resolvidos.
 */
@Composable
fun Dp.hdpRotatePlainPx(rotationDp: Dp, orientation: Orientation = Orientation.LANDSCAPE): Float {
    return LocalDensity.current.run { hdpRotatePlain(rotationDp, orientation).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Uses the base value by default, but when the device is in the specified [orientation],
 * it uses [rotationValue] scaled with the given [finalQualifierResolver].
 * Usage example: `30.wdpRot(45, DpQualifier.WIDTH, Orientation.LANDSCAPE)`
 * → 30.wdp by default, 45 scaled by WIDTH in landscape.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo está na [orientation] especificada,
 * usa [rotationValue] escalado com o [finalQualifierResolver] dado.
 * Exemplo de uso: `30.wdpRot(45, DpQualifier.WIDTH, Orientation.LANDSCAPE)`
 * → 30.wdp por padrão, 45 escalado por WIDTH no paisagem.
 */
@Composable
fun Int.wdpRotate(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE): Dp {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicScaledDp(finalQualifierResolver)
    } else {
        this.toDynamicScaledDp(DpQualifier.WIDTH)
    }
}

/**
 * EN
 * Pixel version of wdpRotate (Int).
 *
 * PT
 * Versão em pixel de wdpRotate (Int).
 */
@Composable
fun Int.wdpRotatePx(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE): Float {
    return LocalDensity.current.run { wdpRotate(rotationValue, finalQualifierResolver, orientation).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun Dp.wdpRotate(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE): Dp {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicScaledDp(finalQualifierResolver)
    } else {
        this.value.toInt().toDynamicScaledDp(finalQualifierResolver)
    }
}

/**
 * EN
 * Pixel version of wdpRotate (Dp).
 *
 * PT
 * Versão em pixel de wdpRotate (Dp).
 */
@Composable
fun Dp.wdpRotatePx(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE): Float {
    return LocalDensity.current.run { wdpRotate(rotationValue, finalQualifierResolver, orientation).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun Dp.wdpRotatePlain(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE): Dp {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicScaledDp(finalQualifierResolver)
    } else {
        this
    }
}

/**
 * EN
 * Pixel version of wdpRotatePlain.
 *
 * PT
 * Versão em pixel de wdpRotatePlain.
 */
@Composable
fun Dp.wdpRotatePlainPx(rotationValue: Int, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE): Float {
    return LocalDensity.current.run { wdpRotatePlain(rotationValue, finalQualifierResolver, orientation).toPx() }
}

/**
 * EN
 * Plain rotation override for **wDP** semantics using **already-resolved** [Dp] values.
 * No resource lookup and no dynamic scaling—only orientation branching. Prefer when nesting
 * extensions or when both sizes are precomputed. For catalog-based rotation values, use
 * [wdpRotatePlain] with an [Int].
 *
 * PT
 * Substituição por rotação **Plain** (semântica **wDP**) com [Dp] **já resolvidos**.
 * Sem recurso nem escalonamento dinâmico—só o desvio por orientação. Recomendado ao aninhar
 * extensões ou quando ambos os valores já foram calculados. Para o valor de rotação via catálogo,
 * use [wdpRotatePlain] com [Int].
 */
@Composable
fun Dp.wdpRotatePlain(rotationDp: Dp, orientation: Orientation = Orientation.LANDSCAPE): Dp {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) rotationDp else this
}

/**
 * EN
 * Pixel version of [wdpRotatePlain] with pre-resolved [Dp] values.
 *
 * PT
 * Versão em pixel de [wdpRotatePlain] com valores [Dp] já resolvidos.
 */
@Composable
fun Dp.wdpRotatePlainPx(rotationDp: Dp, orientation: Orientation = Orientation.LANDSCAPE): Float {
    return LocalDensity.current.run { wdpRotatePlain(rotationDp, orientation).toPx() }
}

// EN Helps extract the activity from context wrapper
// PT Ajuda a extrair a activity de um context wrapper
private tailrec fun android.content.Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

// EN UiModeType facilitator extensions.
// PT Extensões facilitadoras para UiModeType.

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device matches the specified [uiModeType],
 * it uses [modeValue] instead.
 * Usage example: `30.sdpMode(50, UiModeType.TELEVISION)`
 * → 30.sdp by default, 50.sdp on television.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] especificado,
 * usa [modeValue] no lugar.
 * Exemplo de uso: `30.sdpMode(50, UiModeType.TELEVISION)`
 * → 30.sdp por padrão, 50.sdp na televisão.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Int.sdpMode(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.SMALL_WIDTH)
    } else {
        this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH)
    }
}

/**
 * EN
 * Pixel version of sdpMode (Int).
 *
 * PT
 * Versão em pixel de sdpMode (Int).
 */
@Composable
fun Int.sdpModePx(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { sdpMode(modeValue, uiModeType, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@Composable
fun Dp.sdpMode(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.SMALL_WIDTH)
    } else {
        this.value.toInt().toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.SMALL_WIDTH)
    }
}

/**
 * EN
 * Pixel version of sdpMode (Dp).
 *
 * PT
 * Versão em pixel de sdpMode (Dp).
 */
@Composable
fun Dp.sdpModePx(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { sdpMode(modeValue, uiModeType, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@Composable
fun Dp.sdpModePlain(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.SMALL_WIDTH)
    } else {
        this
    }
}

/**
 * EN
 * Pixel version of sdpModePlain.
 *
 * PT
 * Versão em pixel de sdpModePlain.
 */
@Composable
fun Dp.sdpModePlainPx(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { sdpModePlain(modeValue, uiModeType, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Uses the base value by default, but when the device matches the specified [uiModeType],
 * it uses [modeValue] instead.
 * Usage example: `30.hdpMode(50, UiModeType.TELEVISION)`
 * → 30.hdp by default, 50.hdp on television.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] especificado,
 * usa [modeValue] no lugar.
 * Exemplo de uso: `30.hdpMode(50, UiModeType.TELEVISION)`
 * → 30.hdp por padrão, 50.hdp na televisão.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Int.hdpMode(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.HEIGHT)
    } else {
        this.toDynamicScaledDp(DpQualifier.HEIGHT)
    }
}

/**
 * EN
 * Pixel version of hdpMode (Int).
 *
 * PT
 * Versão em pixel de hdpMode (Int).
 */
@Composable
fun Int.hdpModePx(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { hdpMode(modeValue, uiModeType, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.hdpMode(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.HEIGHT)
    } else {
        this.value.toInt().toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.HEIGHT)
    }
}

/**
 * EN
 * Pixel version of hdpMode (Dp).
 *
 * PT
 * Versão em pixel de hdpMode (Dp).
 */
@Composable
fun Dp.hdpModePx(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { hdpMode(modeValue, uiModeType, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.hdpModePlain(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.HEIGHT)
    } else {
        this
    }
}

/**
 * EN
 * Pixel version of hdpModePlain.
 *
 * PT
 * Versão em pixel de hdpModePlain.
 */
@Composable
fun Dp.hdpModePlainPx(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { hdpModePlain(modeValue, uiModeType, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Uses the base value by default, but when the device matches the specified [uiModeType],
 * it uses [modeValue] instead.
 * Usage example: `30.wdpMode(50, UiModeType.TELEVISION)`
 * → 30.wdp by default, 50.wdp on television.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] especificado,
 * usa [modeValue] no lugar.
 * Exemplo de uso: `30.wdpMode(50, UiModeType.TELEVISION)`
 * → 30.wdp por padrão, 50.wdp na televisão.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Int.wdpMode(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.WIDTH)
    } else {
        this.toDynamicScaledDp(DpQualifier.WIDTH)
    }
}

/**
 * EN
 * Pixel version of wdpMode (Int).
 *
 * PT
 * Versão em pixel de wdpMode (Int).
 */
@Composable
fun Int.wdpModePx(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { wdpMode(modeValue, uiModeType, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.wdpMode(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.WIDTH)
    } else {
        this.value.toInt().toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.WIDTH)
    }
}

/**
 * EN
 * Pixel version of wdpMode (Dp).
 *
 * PT
 * Versão em pixel de wdpMode (Dp).
 */
@Composable
fun Dp.wdpModePx(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { wdpMode(modeValue, uiModeType, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.wdpModePlain(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.WIDTH)
    } else {
        this
    }
}

/**
 * EN
 * Pixel version of wdpModePlain.
 *
 * PT
 * Versão em pixel de wdpModePlain.
 */
@Composable
fun Dp.wdpModePlainPx(modeValue: Int, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { wdpModePlain(modeValue, uiModeType, finalQualifierResolver).toPx() }
}

// EN DpQualifier facilitator extensions.
// PT Extensões facilitadoras para DpQualifier.

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 * Usage example: `30.sdpQualifier(50, DpQualifier.SMALL_WIDTH, 600)`
 * → 30.sdp by default, 50.sdp when smallestScreenWidthDp >= 600.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 * Exemplo de uso: `30.sdpQualifier(50, DpQualifier.SMALL_WIDTH, 600)`
 * → 30.sdp por padrão, 50.sdp quando smallestScreenWidthDp >= 600.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Int.sdpQualifier(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (qualifierMatch) {
        qualifiedValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.SMALL_WIDTH)
    } else {
        this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH)
    }
}

/**
 * EN
 * Pixel version of sdpQualifier (Int).
 *
 * PT
 * Versão em pixel de sdpQualifier (Int).
 */
@Composable
fun Int.sdpQualifierPx(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { sdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.sdpQualifier(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (qualifierMatch) {
        qualifiedValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.SMALL_WIDTH)
    } else {
        this.value.toInt().toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.SMALL_WIDTH)
    }
}

/**
 * EN
 * Pixel version of sdpQualifier (Dp).
 *
 * PT
 * Versão em pixel de sdpQualifier (Dp).
 */
@Composable
fun Dp.sdpQualifierPx(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { sdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.sdpQualifierPlain(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (qualifierMatch) {
        qualifiedValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.SMALL_WIDTH)
    } else {
        this
    }
}

/**
 * EN
 * Pixel version of sdpQualifierPlain.
 *
 * PT
 * Versão em pixel de sdpQualifierPlain.
 */
@Composable
fun Dp.sdpQualifierPlainPx(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { sdpQualifierPlain(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 * Usage example: `30.hdpQualifier(50, DpQualifier.HEIGHT, 800)`
 * → 30.hdp by default, 50.hdp when screenHeightDp >= 800.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 * Exemplo de uso: `30.hdpQualifier(50, DpQualifier.HEIGHT, 800)`
 * → 30.hdp por padrão, 50.hdp quando screenHeightDp >= 800.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Int.hdpQualifier(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (qualifierMatch) {
        qualifiedValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.HEIGHT)
    } else {
        this.toDynamicScaledDp(DpQualifier.HEIGHT)
    }
}

/**
 * EN
 * Pixel version of hdpQualifier (Int).
 *
 * PT
 * Versão em pixel de hdpQualifier (Int).
 */
@Composable
fun Int.hdpQualifierPx(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { hdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.hdpQualifier(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (qualifierMatch) {
        qualifiedValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.HEIGHT)
    } else {
        this.value.toInt().toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.HEIGHT)
    }
}

/**
 * EN
 * Pixel version of hdpQualifier (Dp).
 *
 * PT
 * Versão em pixel de hdpQualifier (Dp).
 */
@Composable
fun Dp.hdpQualifierPx(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { hdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.hdpQualifierPlain(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (qualifierMatch) {
        qualifiedValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.HEIGHT)
    } else {
        this
    }
}

/**
 * EN
 * Pixel version of hdpQualifierPlain.
 *
 * PT
 * Versão em pixel de hdpQualifierPlain.
 */
@Composable
fun Dp.hdpQualifierPlainPx(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { hdpQualifierPlain(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 * Usage example: `30.wdpQualifier(50, DpQualifier.WIDTH, 600)`
 * → 30.wdp by default, 50.wdp when screenWidthDp >= 600.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 * Exemplo de uso: `30.wdpQualifier(50, DpQualifier.WIDTH, 600)`
 * → 30.wdp por padrão, 50.wdp quando screenWidthDp >= 600.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Int.wdpQualifier(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (qualifierMatch) {
        qualifiedValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.WIDTH)
    } else {
        this.toDynamicScaledDp(DpQualifier.WIDTH)
    }
}

/**
 * EN
 * Pixel version of wdpQualifier (Int).
 *
 * PT
 * Versão em pixel de wdpQualifier (Int).
 */
@Composable
fun Int.wdpQualifierPx(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { wdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.wdpQualifier(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (qualifierMatch) {
        qualifiedValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.WIDTH)
    } else {
        this.value.toInt().toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.WIDTH)
    }
}

/**
 * EN
 * Pixel version of wdpQualifier (Dp).
 *
 * PT
 * Versão em pixel de wdpQualifier (Dp).
 */
@Composable
fun Dp.wdpQualifierPx(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { wdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.wdpQualifierPlain(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (qualifierMatch) {
        qualifiedValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.WIDTH)
    } else {
        this
    }
}

/**
 * EN
 * Pixel version of wdpQualifierPlain.
 *
 * PT
 * Versão em pixel de wdpQualifierPlain.
 */
@Composable
fun Dp.wdpQualifierPlainPx(qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { wdpQualifierPlain(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

// EN UiModeType + DpQualifier combined facilitator extensions.
// PT Extensões facilitadoras combinadas UiModeType + DpQualifier.

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 * Usage example: `30.sdpScreen(50, UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600)`
 * → 30.sdp by default, 50.sdp on television with sw >= 600.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 * Exemplo de uso: `30.sdpScreen(50, UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600)`
 * → 30.sdp por padrão, 50.sdp na televisão com sw >= 600.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Int.sdpScreen(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.SMALL_WIDTH)
    } else {
        this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH)
    }
}

/**
 * EN
 * Pixel version of sdpScreen (Int).
 *
 * PT
 * Versão em pixel de sdpScreen (Int).
 */
@Composable
fun Int.sdpScreenPx(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { sdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.sdpScreen(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.SMALL_WIDTH)
    } else {
        this.value.toInt().toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.SMALL_WIDTH)
    }
}

/**
 * EN
 * Pixel version of sdpScreen (Dp).
 *
 * PT
 * Versão em pixel de sdpScreen (Dp).
 */
@Composable
fun Dp.sdpScreenPx(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { sdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.sdpScreenPlain(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.SMALL_WIDTH)
    } else {
        this
    }
}

/**
 * EN
 * Pixel version of sdpScreenPlain.
 *
 * PT
 * Versão em pixel de sdpScreenPlain.
 */
@Composable
fun Dp.sdpScreenPlainPx(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { sdpScreenPlain(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 * Usage example: `30.hdpScreen(50, UiModeType.TELEVISION, DpQualifier.HEIGHT, 800)`
 * → 30.hdp by default, 50.hdp on television with height >= 800.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 * Exemplo de uso: `30.hdpScreen(50, UiModeType.TELEVISION, DpQualifier.HEIGHT, 800)`
 * → 30.hdp por padrão, 50.hdp na televisão com height >= 800.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Int.hdpScreen(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.HEIGHT)
    } else {
        this.toDynamicScaledDp(DpQualifier.HEIGHT)
    }
}

/**
 * EN
 * Pixel version of hdpScreen (Int).
 *
 * PT
 * Versão em pixel de hdpScreen (Int).
 */
@Composable
fun Int.hdpScreenPx(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { hdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.hdpScreen(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.HEIGHT)
    } else {
        this.value.toInt().toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.HEIGHT)
    }
}

/**
 * EN
 * Pixel version of hdpScreen (Dp).
 *
 * PT
 * Versão em pixel de hdpScreen (Dp).
 */
@Composable
fun Dp.hdpScreenPx(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { hdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.hdpScreenPlain(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.HEIGHT)
    } else {
        this
    }
}

/**
 * EN
 * Pixel version of hdpScreenPlain.
 *
 * PT
 * Versão em pixel de hdpScreenPlain.
 */
@Composable
fun Dp.hdpScreenPlainPx(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { hdpScreenPlain(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 * Usage example: `30.wdpScreen(50, UiModeType.TELEVISION, DpQualifier.WIDTH, 600)`
 * → 30.wdp by default, 50.wdp on television with width >= 600.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 * Exemplo de uso: `30.wdpScreen(50, UiModeType.TELEVISION, DpQualifier.WIDTH, 600)`
 * → 30.wdp por padrão, 50.wdp na televisão com width >= 600.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Int.wdpScreen(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.WIDTH)
    } else {
        this.toDynamicScaledDp(DpQualifier.WIDTH)
    }
}

/**
 * EN
 * Pixel version of wdpScreen (Int).
 *
 * PT
 * Versão em pixel de wdpScreen (Int).
 */
@Composable
fun Int.wdpScreenPx(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { wdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.wdpScreen(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.WIDTH)
    } else {
        this.value.toInt().toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.WIDTH)
    }
}

/**
 * EN
 * Pixel version of wdpScreen (Dp).
 *
 * PT
 * Versão em pixel de wdpScreen (Dp).
 */
@Composable
fun Dp.wdpScreenPx(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { wdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.wdpScreenPlain(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Dp {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val activity = context.findActivity()
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val currentUiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicScaledDp(finalQualifierResolver ?: DpQualifier.WIDTH)
    } else {
        this
    }
}

/**
 * EN
 * Pixel version of wdpScreenPlain.
 *
 * PT
 * Versão em pixel de wdpScreenPlain.
 */
@Composable
fun Dp.wdpScreenPlainPx(screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null): Float {
    return LocalDensity.current.run { wdpScreenPlain(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver).toPx() }
}
