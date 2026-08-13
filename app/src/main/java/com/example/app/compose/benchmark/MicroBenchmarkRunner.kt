/**
 * @author Bodenberg
 *
 * EN CPU-bound microbenchmark runner for AppDimens SDPS dimension resolution calls.
 *    Runs entirely OFF the main thread (Dispatchers.Default).
 *    Uses warmup + measurement phases with an accumulator to prevent dead-code elimination.
 *    Each call type is timed INDIVIDUALLY; a dedicated block measures the latency of a
 *    SINGLE 1dp call (sdp / hdp / wdp / sdpa / ssp).
 *
 * PT Runner de microbenchmark vinculado à CPU para chamadas de resolução de dimensão AppDimens SDPS.
 *    Executa completamente FORA da thread principal (Dispatchers.Default).
 *    Usa fases de aquecimento + medição com acumulador para prevenir eliminação de código morto.
 *    Cada tipo de chamada é cronometrado INDIVIDUALMENTE; um bloco dedicado mede a latência
 *    de UMA única chamada de 1dp (sdp / hdp / wdp / sdpa / ssp).
 */
package com.example.app.compose.benchmark

import android.content.Context
import android.os.Process
import android.util.Log
import com.appdimens.sdps.code.DimenSdp
import com.appdimens.sdps.code.DimenSsp
import com.appdimens.sdps.code.sdp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.system.measureNanoTime

private const val TAG = "APPDIMENS_MICRO"

/** EN Warmup iterations — results are discarded. Primes the JIT compiler.
 *  PT Iterações de aquecimento — resultados são descartados. Aquece o compilador JIT. */
private const val WARMUP_ITERATIONS = 10_000

/** EN Measurement iterations per call block. PT Iterações de medição por bloco de chamada. */
private const val MEASURE_ITERATIONS = 100_000

/** EN Value used for the single-value with/without aspect-ratio comparison.
 *  PT Valor usado na comparação de valor único com/sem aspect ratio. */
private const val SINGLE_VALUE = 64f

/** EN Discards the call-site warmup transients before a timed block.
 *  PT Descarta os transientes de aquecimento do call-site antes de um bloco cronometrado. */
private const val BLOCK_WARMUP_ITERATIONS = 10_000

/**
 * EN Discards the call-site warmup transients before a timed block.
 * PT Descarta os transientes de aquecimento do call-site antes de um bloco cronometrado.
 */
private fun warmCallSite(call: () -> Float) {
    var acc = 0f
    repeat(BLOCK_WARMUP_ITERATIONS) { acc += call() }
    Log.v(TAG, "Call-site warmup done (acc=$acc)")
}

/**
 * EN Forces the CPU governor to ramp the current core to its peak frequency BEFORE any
 *    measurement, collapsing first-benchmark-family artifacts and run-to-run spread.
 * PT Força o governor da CPU a subir o núcleo atual à frequência de pico ANTES de qualquer
 *    medição, colapsando artefatos da primeira família e a dispersão entre execuções.
 */
private fun thermalRamp(millis: Long = 1_500L) {
    var acc = 0f
    val deadline = System.nanoTime() + millis * 1_000_000L
    var i = 0
    do {
        val x = (i++ and 0xFF) + 1
        acc += kotlin.math.sqrt(x.toDouble()).toFloat()
    } while (System.nanoTime() < deadline)
    Log.v(TAG, "Thermal ramp done (acc=$acc, ${millis}ms)")
}

/**
 * EN Runs the full microbenchmark suite off the main thread and returns structured results.
 *    Sequence: warmup (discarded) → sdp → hdp → wdp → sdpa → SINGLE 1dp (sdp/hdp/wdp/sdpa/ssp)
 *    → single value with/without AR → ext-vs-api probes.
 *
 * PT Executa a suíte completa de microbenchmark fora da thread principal e retorna resultados estruturados.
 *    Sequência: aquecimento (descartado) → sdp → hdp → wdp → sdpa → 1dp ÚNICO (sdp/hdp/wdp/sdpa/ssp)
 *    → valor único com/sem AR → probes ext-vs-api.
 *
 * @param context EN Android context needed for dimension resolution. PT Contexto Android para resolução de dimensão.
 * @param onPhaseChange EN Callback invoked when phase transitions occur. PT Callback invocado nas transições de fase.
 */
suspend fun runMicroBenchmark(
    context: Context,
    onPhaseChange: (BenchmarkPhase) -> Unit
): MicroBenchmarkResult = withContext(Dispatchers.Default) {

    // ── WARMUP PHASE ──────────────────────────────────────────────────────────
    onPhaseChange(BenchmarkPhase.MICRO_WARMUP)

    var warmupAcc = 0f
    repeat(WARMUP_ITERATIONS) {
        warmupAcc += DimenSdp.sdp(context, 100)
        warmupAcc += DimenSdp.hdp(context, 50)
        warmupAcc += DimenSdp.wdp(context, 30)
        warmupAcc += DimenSdp.sdpa(context, 40)
    }
    Log.v(TAG, "Warmup complete (acc=$warmupAcc, ${WARMUP_ITERATIONS} iters discarded)")

    // ── MEASUREMENT PHASE ─────────────────────────────────────────────────────
    onPhaseChange(BenchmarkPhase.MICRO_RUN)
    // EN Hold real-time-ish priority for the whole measurement window.
    // PT Mantém prioridade quase-real para toda a janela de medição.
    try {
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO)
    } catch (_: SecurityException) {
    }

    thermalRamp()

    val startWall = System.currentTimeMillis()

    // ── sdp (XML resource path) ──────────────────────────────────────────────
    var sdpAcc = 0f
    warmCallSite { DimenSdp.sdp(context, 100) }
    val sdpNs = measureNanoTime {
        repeat(MEASURE_ITERATIONS) { sdpAcc += DimenSdp.sdp(context, 100) }
    }
    val sdpAvgNs = sdpNs / MEASURE_ITERATIONS

    // ── hdp ──────────────────────────────────────────────────────────────────
    var hdpAcc = 0f
    warmCallSite { DimenSdp.hdp(context, 50) }
    val hdpNs = measureNanoTime {
        repeat(MEASURE_ITERATIONS) { hdpAcc += DimenSdp.hdp(context, 50) }
    }
    val hdpAvgNs = hdpNs / MEASURE_ITERATIONS

    // ── wdp ──────────────────────────────────────────────────────────────────
    var wdpAcc = 0f
    warmCallSite { DimenSdp.wdp(context, 30) }
    val wdpNs = measureNanoTime {
        repeat(MEASURE_ITERATIONS) { wdpAcc += DimenSdp.wdp(context, 30) }
    }
    val wdpAvgNs = wdpNs / MEASURE_ITERATIONS

    // ── sdpa (aspect-ratio path) ─────────────────────────────────────────────
    var sdpaAcc = 0f
    warmCallSite { DimenSdp.sdpa(context, 40) }
    val sdpaNs = measureNanoTime {
        repeat(MEASURE_ITERATIONS) { sdpaAcc += DimenSdp.sdpa(context, 40) }
    }
    val sdpaAvgNs = sdpaNs / MEASURE_ITERATIONS

    // ── SINGLE 1dp — the per-call cost of ONE dp ──────────────────────────────
    // EN Back-to-back timing of value=1 through every path. This is the number a
    //    developer sees per single .sdp / .hdp / .wdp / .sdpa / .ssp call.
    // PT Cronometragem consecutiva do valor=1 em todos os caminhos. É o número que
    //    um desenvolvedor vê por chamada única .sdp / .hdp / .wdp / .sdpa / .ssp.
    var singleDpAcc = 0f

    warmCallSite { DimenSdp.sdp(context, 1) }
    val singleDpSdpNs = measureNanoTime {
        repeat(MEASURE_ITERATIONS) { singleDpAcc += DimenSdp.sdp(context, 1) }
    }
    val singleDpSdpAvgNs = singleDpSdpNs / MEASURE_ITERATIONS

    warmCallSite { DimenSdp.hdp(context, 1) }
    val singleDpHdpNs = measureNanoTime {
        repeat(MEASURE_ITERATIONS) { singleDpAcc += DimenSdp.hdp(context, 1) }
    }
    val singleDpHdpAvgNs = singleDpHdpNs / MEASURE_ITERATIONS

    warmCallSite { DimenSdp.wdp(context, 1) }
    val singleDpWdpNs = measureNanoTime {
        repeat(MEASURE_ITERATIONS) { singleDpAcc += DimenSdp.wdp(context, 1) }
    }
    val singleDpWdpAvgNs = singleDpWdpNs / MEASURE_ITERATIONS

    warmCallSite { DimenSdp.sdpa(context, 1) }
    val singleDpSdpaNs = measureNanoTime {
        repeat(MEASURE_ITERATIONS) { singleDpAcc += DimenSdp.sdpa(context, 1) }
    }
    val singleDpSdpaAvgNs = singleDpSdpaNs / MEASURE_ITERATIONS

    warmCallSite { DimenSsp.ssp(context, 1) }
    val singleDpSspNs = measureNanoTime {
        repeat(MEASURE_ITERATIONS) { singleDpAcc += DimenSsp.ssp(context, 1) }
    }
    val singleDpSspAvgNs = singleDpSspNs / MEASURE_ITERATIONS

    // ── Single value: same value, with vs without AR ─────────────────────────
    var singleNoArAcc = 0f
    warmCallSite { DimenSdp.sdp(context, SINGLE_VALUE.toInt()) }
    val singleNoArNs = measureNanoTime {
        repeat(MEASURE_ITERATIONS) { singleNoArAcc += DimenSdp.sdp(context, SINGLE_VALUE.toInt()) }
    }
    val singleNoArAvgNs = singleNoArNs / MEASURE_ITERATIONS

    var singleWithArAcc = 0f
    warmCallSite { DimenSdp.sdpa(context, SINGLE_VALUE.toInt()) }
    val singleWithArNs = measureNanoTime {
        repeat(MEASURE_ITERATIONS) { singleWithArAcc += DimenSdp.sdpa(context, SINGLE_VALUE.toInt()) }
    }
    val singleWithArAvgNs = singleWithArNs / MEASURE_ITERATIONS

    // ── Direct-call probes ────────────────────────────────────────────────────
    // EN Isolates the wrapper overhead: extension `100.sdp(ctx)` vs the public
    //    API `DimenSdp.sdp(ctx, 100)`.
    // PT Isola o overhead do wrapper: extensão `100.sdp(ctx)` vs a API pública
    //    `DimenSdp.sdp(ctx, 100)`.
    var extSdpAcc = 0f
    var apiSdpAcc = 0f

    warmCallSite { 100.sdp(context) }
    val extSdpNs = measureNanoTime {
        repeat(MEASURE_ITERATIONS) { extSdpAcc += 100.sdp(context) }
    }
    val extSdpAvgNs = extSdpNs / MEASURE_ITERATIONS

    warmCallSite { DimenSdp.sdp(context, 100) }
    val apiSdpNs = measureNanoTime {
        repeat(MEASURE_ITERATIONS) { apiSdpAcc += DimenSdp.sdp(context, 100) }
    }
    val apiSdpAvgNs = apiSdpNs / MEASURE_ITERATIONS

    val endWall = System.currentTimeMillis()
    val totalWallMs = endWall - startWall

    try {
        Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT)
    } catch (_: SecurityException) {
    }

    // ── Combined average across the 6 core blocks ────────────────────────────
    val totalOps = MEASURE_ITERATIONS * 6
    val combinedNs = sdpNs + hdpNs + wdpNs + sdpaNs + singleNoArNs + singleWithArNs
    val combinedAvgNs = combinedNs / totalOps

    // ── Anti-dead-code accumulator checksum ──────────────────────────────────
    val checksum = sdpAcc + hdpAcc + wdpAcc + sdpaAcc + singleDpAcc +
        singleNoArAcc + singleWithArAcc + extSdpAcc + apiSdpAcc

    val singleDp = SingleDpBenchmarkResult(
        sdpAvgNs  = singleDpSdpAvgNs,
        hdpAvgNs  = singleDpHdpAvgNs,
        wdpAvgNs  = singleDpWdpAvgNs,
        sdpaAvgNs = singleDpSdpaAvgNs,
        sspAvgNs  = singleDpSspAvgNs,
        valuePx   = DimenSdp.sdp(context, 1),
        density   = context.resources.displayMetrics.density,
    )

    // ── Logcat export ─────────────────────────────────────────────────────────
    Log.i(TAG, "╔══════════════════ MICRO BENCHMARK RESULT ══════════════════╗")
    Log.i(TAG, "║ Combined avg: ${combinedAvgNs.formatNs()}/op · Total ops: $totalOps")
    Log.i(TAG, "║ sdp  (XML)   : ${sdpAvgNs.formatNs()}/op")
    Log.i(TAG, "║ hdp  (XML)   : ${hdpAvgNs.formatNs()}/op")
    Log.i(TAG, "║ wdp  (XML)   : ${wdpAvgNs.formatNs()}/op")
    Log.i(TAG, "║ sdpa (+AR)   : ${sdpaAvgNs.formatNs()}/op")
    Log.i(TAG, "║ SINGLE 1dp sdp : ${singleDpSdpAvgNs.formatNs()}/call (value=${"%.2f".format(singleDp.valuePx)}px)")
    Log.i(TAG, "║ SINGLE 1dp hdp : ${singleDpHdpAvgNs.formatNs()}/call")
    Log.i(TAG, "║ SINGLE 1dp wdp : ${singleDpWdpAvgNs.formatNs()}/call")
    Log.i(TAG, "║ SINGLE 1dp sdpa: ${singleDpSdpaAvgNs.formatNs()}/call")
    Log.i(TAG, "║ SINGLE 1dp ssp : ${singleDpSspAvgNs.formatNs()}/call")
    Log.i(TAG, "║ single $SINGLE_VALUE no-AR: ${singleNoArAvgNs.formatNs()}/op")
    Log.i(TAG, "║ single $SINGLE_VALUE +AR  : ${singleWithArAvgNs.formatNs()}/op")
    Log.i(TAG, "║ direct ext 100.sdp(ctx) : ${extSdpAvgNs.formatNs()}/op")
    Log.i(TAG, "║ direct api DimenSdp.sdp : ${apiSdpAvgNs.formatNs()}/op")
    Log.i(TAG, "║ Total wall time: ${totalWallMs}ms")
    Log.i(TAG, "║ Accumulator checksum: $checksum")
    Log.i(TAG, "╚════════════════════════════════════════════════════════════╝")

    MicroBenchmarkResult(
        avgNsPerOp          = combinedAvgNs,
        totalOps            = totalOps,
        totalTimeMs         = totalWallMs,
        sdpBypassAvgNs      = sdpAvgNs,
        hdpBypassAvgNs      = hdpAvgNs,
        wdpBypassAvgNs      = wdpAvgNs,
        sdpaCacheAvgNs      = sdpaAvgNs,
        singleNoArAvgNs     = singleNoArAvgNs,
        singleWithArAvgNs   = singleWithArAvgNs,
        singleValue         = SINGLE_VALUE,
        extSdpAvgNs         = extSdpAvgNs,
        apiSdpAvgNs         = apiSdpAvgNs,
        accumulatorChecksum = checksum,
        singleDp            = singleDp,
    )
}