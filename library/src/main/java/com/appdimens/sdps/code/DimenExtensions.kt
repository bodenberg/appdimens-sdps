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
import com.appdimens.sdps.common.DpQualifier
import com.appdimens.sdps.common.Orientation
import com.appdimens.sdps.common.UiModeType

// EN Extension functions for quick dynamic scaling (Dp) from code (non-Compose).
// PT Funções de extensão para escalonamento dinâmico rápido (Dp) a partir de código (não-Compose).

/** EN Quick resolution for Smallest Width (sdp). PT Resolução rápida para Smallest Width (sdp). @see DimenSdp.sdp */
fun Int.sdp(context: Context): Float = DimenSdp.sdp(context, this)

/** EN Quick resolution for sdp, portrait -> Height. PT Resolução rápida para sdp, retrato -> Altura. @see DimenSdp.sdpPh */
fun Int.sdpPh(context: Context): Float = DimenSdp.sdpPh(context, this)

/** EN Quick resolution for sdp, landscape -> Height. PT Resolução rápida para sdp, paisagem -> Altura. @see DimenSdp.sdpLh */
fun Int.sdpLh(context: Context): Float = DimenSdp.sdpLh(context, this)

/** EN Quick resolution for sdp, portrait -> Width. PT Resolução rápida para sdp, retrato -> Largura. @see DimenSdp.sdpPw */
fun Int.sdpPw(context: Context): Float = DimenSdp.sdpPw(context, this)

/** EN Quick resolution for sdp, landscape -> Width. PT Resolução rápida para sdp, paisagem -> Largura. @see DimenSdp.sdpLw */
fun Int.sdpLw(context: Context): Float = DimenSdp.sdpLw(context, this)

/** EN Quick resolution for Screen Height (hdp). PT Resolução rápida para Altura da Tela (hdp). @see DimenSdp.hdp */
fun Int.hdp(context: Context): Float = DimenSdp.hdp(context, this)

/** EN Quick resolution for hdp, landscape -> Width. PT Resolução rápida para hdp, paisagem -> Largura. @see DimenSdp.hdpLw */
fun Int.hdpLw(context: Context): Float = DimenSdp.hdpLw(context, this)

/** EN Quick resolution for hdp, portrait -> Width. PT Resolução rápida para hdp, retrato -> Largura. @see DimenSdp.hdpPw */
fun Int.hdpPw(context: Context): Float = DimenSdp.hdpPw(context, this)

/** EN Quick resolution for Screen Width (wdp). PT Resolução rápida para Largura da Tela (wdp). @see DimenSdp.wdp */
fun Int.wdp(context: Context): Float = DimenSdp.wdp(context, this)

/** EN Quick resolution for wdp, landscape -> Height. PT Resolução rápida para wdp, paisagem -> Altura. @see DimenSdp.wdpLh */
fun Int.wdpLh(context: Context): Float = DimenSdp.wdpLh(context, this)

/** EN Quick resolution for wdp, portrait -> Height. PT Resolução rápida para wdp, retrato -> Altura. @see DimenSdp.wdpPh */
fun Int.wdpPh(context: Context): Float = DimenSdp.wdpPh(context, this)


// EN Resource ID variants
// PT Variantes que retornam o ID de recurso

/** EN Quick resolution for Smallest Width resource ID (sdpRes). PT Resolução rápida para ID de recurso Smallest Width (sdpRes). @see DimenSdp.sdpRes */
fun Int.sdpRes(context: Context): Int = DimenSdp.sdpRes(context, this)

/** EN Quick resolution for sdp resource ID, portrait -> Height. PT Resolução rápida para ID de recurso sdp, retrato -> Altura. @see DimenSdp.sdpPhRes */
fun Int.sdpPhRes(context: Context): Int = DimenSdp.sdpPhRes(context, this)

/** EN Quick resolution for sdp resource ID, landscape -> Height. PT Resolução rápida para ID de recurso sdp, paisagem -> Altura. @see DimenSdp.sdpLhRes */
fun Int.sdpLhRes(context: Context): Int = DimenSdp.sdpLhRes(context, this)

/** EN Quick resolution for sdp resource ID, portrait -> Width. PT Resolução rápida para ID de recurso sdp, retrato -> Largura. @see DimenSdp.sdpPwRes */
fun Int.sdpPwRes(context: Context): Int = DimenSdp.sdpPwRes(context, this)

/** EN Quick resolution for sdp resource ID, landscape -> Width. PT Resolução rápida para ID de recurso sdp, paisagem -> Largura. @see DimenSdp.sdpLwRes */
fun Int.sdpLwRes(context: Context): Int = DimenSdp.sdpLwRes(context, this)

/** EN Quick resolution for Screen Height resource ID (hdpRes). PT Resolução rápida para ID de recurso Altura da Tela (hspRes). @see DimenSdp.hdpRes */
fun Int.hdpRes(context: Context): Int = DimenSdp.hdpRes(context, this)

/** EN Quick resolution for hdp resource ID, landscape -> Width. PT Resolução rápida para ID de recurso hdp, paisagem -> Largura. @see DimenSdp.hdpLwRes */
fun Int.hdpLwRes(context: Context): Int = DimenSdp.hdpLwRes(context, this)

/** EN Quick resolution for hdp resource ID, portrait -> Width. PT Resolução rápida para ID de recurso hdp, retrato -> Largura. @see DimenSdp.hdpPwRes */
fun Int.hdpPwRes(context: Context): Int = DimenSdp.hdpPwRes(context, this)

/** EN Quick resolution for Screen Width resource ID (wdpRes). PT Resolução rápida para ID de recurso Largura da Tela (wdpRes). @see DimenSdp.wdpRes */
fun Int.wdpRes(context: Context): Int = DimenSdp.wdpRes(context, this)

/** EN Quick resolution for wdp resource ID, landscape -> Height. PT Resolução rápida para ID de recurso wdp, paisagem -> Altura. @see DimenSdp.wdpLhRes */
fun Int.wdpLhRes(context: Context): Int = DimenSdp.wdpLhRes(context, this)

/** EN Quick resolution for wdp resource ID, portrait -> Height. PT Resolução rápida para ID de recurso wdp, retrato -> Altura. @see DimenSdp.wdpPhRes */
fun Int.wdpPhRes(context: Context): Int = DimenSdp.wdpPhRes(context, this)


// EN Rotation facilitator extensions for code.
// PT Extensões facilitadoras de rotação para código.

/** EN Facilitator for sdp with rotation override. PT Facilitador para sdp com substituição por rotação. @see DimenSdp.sdpRotate */
fun Int.sdpRotate(
    context: Context,
    rotationValue: Int,
    finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE
): Float = DimenSdp.sdpRotate(context, this, rotationValue, finalQualifierResolver, orientation)

/** EN Facilitator for hdp with rotation override. PT Facilitador para hdp com substituição por rotação. @see DimenSdp.hdpRotate */
fun Int.hdpRotate(
    context: Context,
    rotationValue: Int,
    finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT,
    orientation: Orientation = Orientation.LANDSCAPE
): Float = DimenSdp.hdpRotate(context, this, rotationValue, finalQualifierResolver, orientation)

/** EN Facilitator for wdp with rotation override. PT Facilitador para wdp com substituição por rotação. @see DimenSdp.wdpRotate */
fun Int.wdpRotate(
    context: Context,
    rotationValue: Int,
    finalQualifierResolver: DpQualifier = DpQualifier.WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE
): Float = DimenSdp.wdpRotate(context, this, rotationValue, finalQualifierResolver, orientation)

// EN UiModeType facilitator extensions for code.
// PT Extensões facilitadoras de UiModeType para código.

/** EN Facilitator for sdp with UiModeType override. PT Facilitador para sdp com substituição por UiModeType. @see DimenSdp.sdpMode */
fun Int.sdpMode(
    context: Context,
    modeValue: Int,
    uiModeType: UiModeType,
    foldingFeature: androidx.window.layout.FoldingFeature? = null,
    finalQualifierResolver: DpQualifier? = null
): Float = DimenSdp.sdpMode(context, this, modeValue, uiModeType, foldingFeature, finalQualifierResolver)

/** EN Facilitator for hdp with UiModeType override. PT Facilitador para hdp com substituição por UiModeType. @see DimenSdp.hdpMode */
fun Int.hdpMode(
    context: Context,
    modeValue: Int,
    uiModeType: UiModeType,
    foldingFeature: androidx.window.layout.FoldingFeature? = null,
    finalQualifierResolver: DpQualifier? = null
): Float = DimenSdp.hdpMode(context, this, modeValue, uiModeType, foldingFeature, finalQualifierResolver)

/** EN Facilitator for wdp with UiModeType override. PT Facilitador para wdp com substituição por UiModeType. @see DimenSdp.wdpMode */
fun Int.wdpMode(
    context: Context,
    modeValue: Int,
    uiModeType: UiModeType,
    foldingFeature: androidx.window.layout.FoldingFeature? = null,
    finalQualifierResolver: DpQualifier? = null
): Float = DimenSdp.wdpMode(context, this, modeValue, uiModeType, foldingFeature, finalQualifierResolver)

// EN DpQualifier facilitator extensions for code.
// PT Funções facilitadoras de DpQualifier para código.

/** EN Facilitator for sdp with DpQualifier override. PT Facilitador para sdp com substituição por DpQualifier. @see DimenSdp.sdpQualifier */
fun Int.sdpQualifier(
    context: Context,
    qualifiedValue: Int,
    qualifierType: DpQualifier,
    qualifierValue: Int,
    finalQualifierResolver: DpQualifier? = null
): Float = DimenSdp.sdpQualifier(context, this, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver)

/** EN Facilitator for hdp with DpQualifier override. PT Facilitador para hdp com substituição por DpQualifier. @see DimenSdp.hdpQualifier */
fun Int.hdpQualifier(
    context: Context,
    qualifiedValue: Int,
    qualifierType: DpQualifier,
    qualifierValue: Int,
    finalQualifierResolver: DpQualifier? = null
): Float = DimenSdp.hdpQualifier(context, this, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver)

/** EN Facilitator for wdp with DpQualifier override. PT Facilitador para wdp com substituição por DpQualifier. @see DimenSdp.wdpQualifier */
fun Int.wdpQualifier(
    context: Context,
    qualifiedValue: Int,
    qualifierType: DpQualifier,
    qualifierValue: Int,
    finalQualifierResolver: DpQualifier? = null
): Float = DimenSdp.wdpQualifier(context, this, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver)

// EN UiModeType + DpQualifier combined facilitator extensions for code.
// PT Extensões facilitadoras combinadas UiModeType + DpQualifier para código.

/** EN Facilitator for sdp with combined UiModeType + DpQualifier override. PT Facilitador para sdp com substituição combinada UiModeType + DpQualifier. @see DimenSdp.sdpScreen */
fun Int.sdpScreen(
    context: Context,
    screenValue: Int,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Int,
    foldingFeature: androidx.window.layout.FoldingFeature? = null,
    finalQualifierResolver: DpQualifier? = null
): Float = DimenSdp.sdpScreen(context, this, screenValue, uiModeType, qualifierType, qualifierValue, foldingFeature, finalQualifierResolver)

/** EN Facilitator for hdp with combined UiModeType + DpQualifier override. PT Facilitador para hdp com substituição combinada UiModeType + DpQualifier. @see DimenSdp.hdpScreen */
fun Int.hdpScreen(
    context: Context,
    screenValue: Int,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Int,
    foldingFeature: androidx.window.layout.FoldingFeature? = null,
    finalQualifierResolver: DpQualifier? = null
): Float = DimenSdp.hdpScreen(context, this, screenValue, uiModeType, qualifierType, qualifierValue, foldingFeature, finalQualifierResolver)

/** EN Facilitator for wdp with combined UiModeType + DpQualifier override. PT Facilitador para wdp com substituição combinada UiModeType + DpQualifier. @see DimenSdp.wdpScreen */
fun Int.wdpScreen(
    context: Context,
    screenValue: Int,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Int,
    foldingFeature: androidx.window.layout.FoldingFeature? = null,
    finalQualifierResolver: DpQualifier? = null
): Float = DimenSdp.wdpScreen(context, this, screenValue, uiModeType, qualifierType, qualifierValue, foldingFeature, finalQualifierResolver)
