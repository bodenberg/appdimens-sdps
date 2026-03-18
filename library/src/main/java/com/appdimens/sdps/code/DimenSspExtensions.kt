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

// EN Extension functions for quick dynamic text scaling (Sp) from code (non-Compose).
// PT Funções de extensão para escalonamento dinâmico rápido de texto (Sp) a partir de código (não-Compose).

/** EN Quick resolution for Smallest Width (ssp). PT Resolução rápida para Smallest Width (ssp). @see DimenSsp.ssp */
fun Int.ssp(context: Context): Float = DimenSsp.ssp(context, this)

/** EN Quick resolution for ssp, portrait -> Height. PT Resolução rápida para ssp, retrato -> Altura. @see DimenSsp.sspPh */
fun Int.sspPh(context: Context): Float = DimenSsp.sspPh(context, this)

/** EN Quick resolution for ssp, landscape -> Height. PT Resolução rápida para ssp, paisagem -> Altura. @see DimenSsp.sspLh */
fun Int.sspLh(context: Context): Float = DimenSsp.sspLh(context, this)

/** EN Quick resolution for ssp, portrait -> Width. PT Resolução rápida para ssp, retrato -> Largura. @see DimenSsp.sspPw */
fun Int.sspPw(context: Context): Float = DimenSsp.sspPw(context, this)

/** EN Quick resolution for ssp, landscape -> Width. PT Resolução rápida para ssp, paisagem -> Largura. @see DimenSsp.sspLw */
fun Int.sspLw(context: Context): Float = DimenSsp.sspLw(context, this)

/** EN Quick resolution for Screen Height (hsp). PT Resolução rápida para Altura da Tela (hsp). @see DimenSsp.hsp */
fun Int.hsp(context: Context): Float = DimenSsp.hsp(context, this)

/** EN Quick resolution for hsp, landscape -> Width. PT Resolução rápida para hsp, paisagem -> Largura. @see DimenSsp.hspLw */
fun Int.hspLw(context: Context): Float = DimenSsp.hspLw(context, this)

/** EN Quick resolution for hsp, portrait -> Width. PT Resolução rápida para hsp, retrato -> Largura. @see DimenSsp.hspPw */
fun Int.hspPw(context: Context): Float = DimenSsp.hspPw(context, this)

/** EN Quick resolution for Screen Width (wsp). PT Resolução rápida para Largura da Tela (wsp). @see DimenSsp.wsp */
fun Int.wsp(context: Context): Float = DimenSsp.wsp(context, this)

/** EN Quick resolution for wsp, landscape -> Height. PT Resolução rápida para wsp, paisagem -> Altura. @see DimenSsp.wspLh */
fun Int.wspLh(context: Context): Float = DimenSsp.wspLh(context, this)

/** EN Quick resolution for wsp, portrait -> Height. PT Resolução rápida para wsp, retrato -> Altura. @see DimenSsp.wspPh */
fun Int.wspPh(context: Context): Float = DimenSsp.wspPh(context, this)

// EN WITHOUT FONT SCALE variants
// PT Variantes SEM ESCALA DE FONTE

/** EN ssp without font scale. PT ssp sem escala de fonte. @see DimenSsp.sem */
fun Int.sem(context: Context): Float = DimenSsp.sem(context, this)

/** EN ssp without font scale, portrait -> Height. PT ssp sem escala de fonte, retrato -> Altura. @see DimenSsp.semPh */
fun Int.semPh(context: Context): Float = DimenSsp.semPh(context, this)

/** EN ssp without font scale, landscape -> Height. PT ssp sem escala de fonte, paisagem -> Altura. @see DimenSsp.semLh */
fun Int.semLh(context: Context): Float = DimenSsp.semLh(context, this)

/** EN ssp without font scale, portrait -> Width. PT ssp sem escala de fonte, retrato -> Largura. @see DimenSsp.semPw */
fun Int.semPw(context: Context): Float = DimenSsp.semPw(context, this)

/** EN ssp without font scale, landscape -> Width. PT ssp sem escala de fonte, paisagem -> Largura. @see DimenSsp.semLw */
fun Int.semLw(context: Context): Float = DimenSsp.semLw(context, this)

/** EN hsp without font scale. PT hsp sem escala de fonte. @see DimenSsp.hem */
fun Int.hem(context: Context): Float = DimenSsp.hem(context, this)

/** EN hsp without font scale, landscape -> Width. PT hsp sem escala de fonte, paisagem -> Largura. @see DimenSsp.hemLw */
fun Int.hemLw(context: Context): Float = DimenSsp.hemLw(context, this)

/** EN hsp without font scale, portrait -> Width. PT hsp sem escala de fonte, retrato -> Largura. @see DimenSsp.hemPw */
fun Int.hemPw(context: Context): Float = DimenSsp.hemPw(context, this)

/** EN wsp without font scale. PT wsp sem escala de fonte. @see DimenSsp.wem */
fun Int.wem(context: Context): Float = DimenSsp.wem(context, this)

/** EN wsp without font scale, landscape -> Height. PT wsp sem escala de fonte, paisagem -> Altura. @see DimenSsp.wemLh */
fun Int.wemLh(context: Context): Float = DimenSsp.wemLh(context, this)

/** EN wsp without font scale, portrait -> Height. PT wsp sem escala de fonte, retrato -> Altura. @see DimenSsp.wemPh */
fun Int.wemPh(context: Context): Float = DimenSsp.wemPh(context, this)

// EN Resource ID variants
// PT Variantes que retornam o ID de recurso

/** EN Quick resolution for Smallest Width resource ID (sspRes). PT Resolução rápida para ID de recurso Smallest Width (sspRes). @see DimenSsp.sspRes */
fun Int.sspRes(context: Context): Int = DimenSsp.sspRes(context, this)

/** EN Quick resolution for ssp resource ID, portrait -> Height. PT Resolução rápida para ID de recurso ssp, retrato -> Altura. @see DimenSsp.sspPhRes */
fun Int.sspPhRes(context: Context): Int = DimenSsp.sspPhRes(context, this)

/** EN Quick resolution for ssp resource ID, landscape -> Height. PT Resolução rápida para ID de recurso ssp, paisagem -> Altura. @see DimenSsp.sspLhRes */
fun Int.sspLhRes(context: Context): Int = DimenSsp.sspLhRes(context, this)

/** EN Quick resolution for ssp resource ID, portrait -> Width. PT Resolução rápida para ID de recurso ssp, retrato -> Largura. @see DimenSsp.sspPwRes */
fun Int.sspPwRes(context: Context): Int = DimenSsp.sspPwRes(context, this)

/** EN Quick resolution for ssp resource ID, landscape -> Width. PT Resolução rápida para ID de recurso ssp, paisagem -> Largura. @see DimenSsp.sspLwRes */
fun Int.sspLwRes(context: Context): Int = DimenSsp.sspLwRes(context, this)

/** EN Quick resolution for Screen Height resource ID (hspRes). PT Resolução rápida para ID de recurso Altura da Tela (hspRes). @see DimenSsp.hspRes */
fun Int.hspRes(context: Context): Int = DimenSsp.hspRes(context, this)

/** EN Quick resolution for hsp resource ID, landscape -> Width. PT Resolução rápida para ID de recurso hsp, paisagem -> Largura. @see DimenSsp.hspLwRes */
fun Int.hspLwRes(context: Context): Int = DimenSsp.hspLwRes(context, this)

/** EN Quick resolution for hsp resource ID, portrait -> Width. PT Resolução rápida para ID de recurso hsp, retrato -> Largura. @see DimenSsp.hspPwRes */
fun Int.hspPwRes(context: Context): Int = DimenSsp.hspPwRes(context, this)

/** EN Quick resolution for Screen Width resource ID (wspRes). PT Resolução rápida para ID de recurso Largura da Tela (wspRes). @see DimenSsp.wspRes */
fun Int.wspRes(context: Context): Int = DimenSsp.wspRes(context, this)

/** EN Quick resolution for wsp resource ID, landscape -> Height. PT Resolução rápida para ID de recurso wsp, paisagem -> Altura. @see DimenSsp.wspLhRes */
fun Int.wspLhRes(context: Context): Int = DimenSsp.wspLhRes(context, this)

/** EN Quick resolution for wsp resource ID, portrait -> Height. PT Resolução rápida para ID de recurso wsp, retrato -> Altura. @see DimenSsp.wspPhRes */
fun Int.wspPhRes(context: Context): Int = DimenSsp.wspPhRes(context, this)


// EN Rotation facilitator extensions for Sp from code.
// PT Extensões facilitadoras de rotação para Sp de código.

/** EN Facilitator for ssp with rotation override. PT Facilitador para ssp com substituição por rotação. @see DimenSsp.sspRotate */
fun Int.sspRotate(
    context: Context,
    rotationValue: Int,
    finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true
): Float = DimenSsp.sspRotate(context, this, rotationValue, finalQualifierResolver, orientation, fontScale)

/** EN Facilitator for hsp with rotation override. PT Facilitador para hsp com substituição por rotação. @see DimenSsp.hspRotate */
fun Int.hspRotate(
    context: Context,
    rotationValue: Int,
    finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true
): Float = DimenSsp.hspRotate(context, this, rotationValue, finalQualifierResolver, orientation, fontScale)

/** EN Facilitator for wsp with rotation override. PT Facilitador para wsp com substituição por rotação. @see DimenSsp.wspRotate */
fun Int.wspRotate(
    context: Context,
    rotationValue: Int,
    finalQualifierResolver: DpQualifier = DpQualifier.WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true
): Float = DimenSsp.wspRotate(context, this, rotationValue, finalQualifierResolver, orientation, fontScale)

// EN UiModeType facilitator extensions for Sp from code.
// PT Extensões facilitadoras de UiModeType para Sp de código.

/** EN Facilitator for ssp with UiModeType override. PT Facilitador para ssp com substituição por UiModeType. @see DimenSsp.sspMode */
fun Int.sspMode(
    context: Context,
    modeValue: Int,
    uiModeType: UiModeType,
    foldingFeature: androidx.window.layout.FoldingFeature? = null,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
): Float = DimenSsp.sspMode(context, this, modeValue, uiModeType, foldingFeature, finalQualifierResolver, fontScale)

/** EN Facilitator for hsp with UiModeType override. PT Facilitador para hsp com substituição por UiModeType. @see DimenSsp.hspMode */
fun Int.hspMode(
    context: Context,
    modeValue: Int,
    uiModeType: UiModeType,
    foldingFeature: androidx.window.layout.FoldingFeature? = null,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
): Float = DimenSsp.hspMode(context, this, modeValue, uiModeType, foldingFeature, finalQualifierResolver, fontScale)

/** EN Facilitator for wsp with UiModeType override. PT Facilitador para wsp com substituição por UiModeType. @see DimenSsp.wspMode */
fun Int.wspMode(
    context: Context,
    modeValue: Int,
    uiModeType: UiModeType,
    foldingFeature: androidx.window.layout.FoldingFeature? = null,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
): Float = DimenSsp.wspMode(context, this, modeValue, uiModeType, foldingFeature, finalQualifierResolver, fontScale)

// EN DpQualifier facilitator extensions for Sp from code.
// PT Funções facilitadoras de DpQualifier para Sp de código.

/** EN Facilitator for ssp with DpQualifier override. PT Facilitador para ssp com substituição por DpQualifier. @see DimenSsp.sspQualifier */
fun Int.sspQualifier(
    context: Context,
    qualifiedValue: Int,
    qualifierType: DpQualifier,
    qualifierValue: Int,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
): Float = DimenSsp.sspQualifier(context, this, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale)

/** EN Facilitator for hsp with DpQualifier override. PT Facilitador para hsp com substituição por DpQualifier. @see DimenSsp.hspQualifier */
fun Int.hspQualifier(
    context: Context,
    qualifiedValue: Int,
    qualifierType: DpQualifier,
    qualifierValue: Int,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
): Float = DimenSsp.hspQualifier(context, this, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale)

/** EN Facilitator for wsp with DpQualifier override. PT Facilitador para wsp com substituição por DpQualifier. @see DimenSsp.wspQualifier */
fun Int.wspQualifier(
    context: Context,
    qualifiedValue: Int,
    qualifierType: DpQualifier,
    qualifierValue: Int,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
): Float = DimenSsp.wspQualifier(context, this, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale)

// EN UiModeType + DpQualifier combined facilitator extensions for Sp from code.
// PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp de código.

/** EN Facilitator for ssp with combined UiModeType + DpQualifier override. PT Facilitador para ssp com substituição combinada UiModeType + DpQualifier. @see DimenSsp.sspScreen */
fun Int.sspScreen(
    context: Context,
    screenValue: Int,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Int,
    foldingFeature: androidx.window.layout.FoldingFeature? = null,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
): Float = DimenSsp.sspScreen(context, this, screenValue, uiModeType, qualifierType, qualifierValue, foldingFeature, finalQualifierResolver, fontScale)

/** EN Facilitator for hsp with combined UiModeType + DpQualifier override. PT Facilitador para hsp com substituição combinada UiModeType + DpQualifier. @see DimenSsp.hspScreen */
fun Int.hspScreen(
    context: Context,
    screenValue: Int,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Int,
    foldingFeature: androidx.window.layout.FoldingFeature? = null,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
): Float = DimenSsp.hspScreen(context, this, screenValue, uiModeType, qualifierType, qualifierValue, foldingFeature, finalQualifierResolver, fontScale)

/** EN Facilitator for wsp with combined UiModeType + DpQualifier override. PT Facilitador para wsp com substituição combinada UiModeType + DpQualifier. @see DimenSsp.wspScreen */
fun Int.wspScreen(
    context: Context,
    screenValue: Int,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Int,
    foldingFeature: androidx.window.layout.FoldingFeature? = null,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
): Float = DimenSsp.wspScreen(context, this, screenValue, uiModeType, qualifierType, qualifierValue, foldingFeature, finalQualifierResolver, fontScale)
