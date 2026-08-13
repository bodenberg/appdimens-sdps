/**
 * @author Bodenberg
 *
 * EN CPU-bound calculation benchmark runner for AppDimens SDPS.
 *    Measures mixed-type (sdp, hdp, wdp, sdpa) resolution latency in a tight loop
 *    to simulate real-world usage patterns.
 *
 * PT Runner de benchmark de cálculo vinculado à CPU para AppDimens SDPS.
 *    Mede a latência de resolução de tipos mistos (sdp, hdp, wdp, sdpa) em um loop
 *    fechado para simular padrões de uso do mundo real.
 */
package com.example.app.compose.benchmark

import android.content.Context
import android.util.Log
import com.appdimens.sdps.code.DimenSdp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.system.measureNanoTime

private const val TAG = "APPDIMENS_CALC"
private const val REPEAT_COUNT = 10_000
private const val CALLS_PER_BLOCK = 4

/**
 * EN Runs the calculation benchmark (mixed-path test) off the main thread.
 * PT Executa o benchmark de cálculo (teste de caminho misto) fora da thread principal.
 *
 * @param context EN Android context. PT Contexto Android.
 * @param onPhaseChange EN Callback for phase transitions. PT Callback para transições de fase.
 */
suspend fun runCalculationBenchmark(
    context: Context,
    onPhaseChange: (BenchmarkPhase) -> Unit
): CalculationBenchmarkResult = withContext(Dispatchers.Default) {

    // ── WARMUP ──────────────────────────────────────────────────────────────
    onPhaseChange(BenchmarkPhase.CALC_WARMUP)

    // EN Brief JIT priming with 1/10th of iterations
    // PT Breve aquecimento do JIT com 1/10 das iterações
    repeat(1000) {
        DimenSdp.sdp(context, 100)
        DimenSdp.hdp(context, 50)
        DimenSdp.wdp(context, 30)
        DimenSdp.sdpa(context, 40)
    }
    delay(100) // EN Settling. PT Estabilização.

    // ── MEASUREMENT ─────────────────────────────────────────────────────────
    onPhaseChange(BenchmarkPhase.CALC_RUN)

    val totalNs = measureNanoTime {
        repeat(REPEAT_COUNT) {
            DimenSdp.sdp(context, 100)
            DimenSdp.hdp(context, 50)
            DimenSdp.wdp(context, 30)
            DimenSdp.sdpa(context, 40)
        }
    }

    val totalOps = REPEAT_COUNT * CALLS_PER_BLOCK
    val avg = totalNs / totalOps

    val throughputStr = "sw+h+w (+AR) × $REPEAT_COUNT iters ($totalOps calls)"

    // EN Logcat export exactly as requested
    // PT Exportação do Logcat exatamente como solicitado
    println("--- UI_BENCHMARK_RESULT: $avg ns ($throughputStr) ---")
    Log.i(TAG, "Calculation Result: $avg ns avg/resolution ($throughputStr)")

    CalculationBenchmarkResult(
        avgNsPerRes = avg,
        totalOps    = totalOps,
        throughput  = throughputStr,
    )
}