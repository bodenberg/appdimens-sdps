/**
 * @author Bodenberg
 *
 * EN Data models for benchmark results of the AppDimens SDPS library.
 *    MicroBenchmarkResult: per-call-type averages from CPU-bound measurement.
 *    SingleDpBenchmarkResult: latency of a SINGLE 1dp call (user-facing metric).
 *    MacroBenchmarkResult: scroll timing and cost estimates from UI-bound measurement.
 * PT Modelos de dados para resultados de benchmark da biblioteca AppDimens SDPS.
 *    MicroBenchmarkResult: médias por tipo de chamada da medição vinculada à CPU.
 *    SingleDpBenchmarkResult: latência de UMA única chamada de 1dp (métrica de face ao usuário).
 *    MacroBenchmarkResult: tempo de rolagem e estimativas de custo da medição vinculada à UI.
 */
package com.example.app.compose.benchmark

/**
 * EN Latency of a SINGLE 1dp call through each resolution path.
 * PT Latência de UMA única chamada de 1dp em cada caminho de resolução.
 *
 * @param sdpAvgNs  EN Avg ns per single `DimenSdp.sdp(ctx, 1)` call. PT Média ns por chamada única `DimenSdp.sdp(ctx, 1)`.
 * @param hdpAvgNs  EN Avg ns per single `DimenSdp.hdp(ctx, 1)` call. PT Média ns por chamada única `DimenSdp.hdp(ctx, 1)`.
 * @param wdpAvgNs  EN Avg ns per single `DimenSdp.wdp(ctx, 1)` call. PT Média ns por chamada única `DimenSdp.wdp(ctx, 1)`.
 * @param sdpaAvgNs EN Avg ns per single `DimenSdp.sdpa(ctx, 1)` call (with AR). PT Média ns por chamada única `DimenSdp.sdpa(ctx, 1)` (com AR).
 * @param sspAvgNs  EN Avg ns per single `DimenSsp.ssp(ctx, 1)` call (font path). PT Média ns por chamada única `DimenSsp.ssp(ctx, 1)` (caminho de fonte).
 * @param valuePx   EN Resolved pixel value of 1.sdp on this device. PT Valor em pixel resolvido de 1.sdp neste device.
 * @param density   EN Display density at measurement time. PT Densidade do display na medição.
 */
data class SingleDpBenchmarkResult(
    val sdpAvgNs: Long,
    val hdpAvgNs: Long,
    val wdpAvgNs: Long,
    val sdpaAvgNs: Long,
    val sspAvgNs: Long,
    val valuePx: Float,
    val density: Float,
)

/**
 * EN Results from the Microbenchmark runner.
 *    Each call type (sdp, hdp, wdp = XML bypass path; sdpa = aspect-ratio path) is timed
 *    individually to expose the performance of each resolution route.
 * PT Resultados do runner de Microbenchmark.
 *    Cada tipo de chamada (sdp, hdp, wdp = caminho XML bypass; sdpa = caminho aspect ratio)
 *    é cronometrado individualmente para expor o desempenho de cada rota de resolução.
 *
 * @param avgNsPerOp    EN Combined average ns per operation across the 6 core blocks.
 *                      PT Média combinada ns/op nos 6 blocos centrais.
 * @param totalOps      EN Total core operations measured. PT Total de operações centrais medidas.
 * @param totalTimeMs   EN Total elapsed measurement time in ms. PT Tempo total de medição em ms.
 * @param sdpBypassAvgNs EN Avg ns per sdp() call (XML resource path). PT Média ns por chamada sdp() (recurso XML).
 * @param hdpBypassAvgNs EN Avg ns per hdp() call. PT Média ns por chamada hdp().
 * @param wdpBypassAvgNs EN Avg ns per wdp() call. PT Média ns por chamada wdp().
 * @param sdpaCacheAvgNs EN Avg ns per sdpa() call (aspect-ratio path). PT Média ns por chamada sdpa() (aspect ratio).
 * @param singleNoArAvgNs EN Avg ns per single-value resolution WITHOUT aspect ratio. PT Média ns por resolução de um único valor SEM aspect ratio.
 * @param singleWithArAvgNs EN Avg ns per single-value resolution WITH aspect ratio. PT Média ns por resolução de um único valor COM aspect ratio.
 * @param singleValue EN The value used for the with/without AR comparison. PT Valor usado na comparação com/sem AR.
 * @param extSdpAvgNs EN SCALED-only: avg ns/op for the direct extension call `100.sdp(ctx)`.
 *                     PT Apenas SCALED: média ns/op da chamada de extensão direta `100.sdp(ctx)`.
 * @param apiSdpAvgNs EN SCALED-only: avg ns/op for the public API call `DimenSdp.sdp(ctx, 100)`.
 *                     PT Apenas SCALED: média ns/op da chamada de API pública `DimenSdp.sdp(ctx, 100)`.
 * @param accumulatorChecksum EN Accumulator value to prove results were consumed (anti-dead-code). PT Valor acumulador.
 * @param singleDp EN Single-1dp latency measurement. PT Medição de latência de um único 1dp.
 */
data class MicroBenchmarkResult(
    val avgNsPerOp: Long,
    val totalOps: Int,
    val totalTimeMs: Long,
    val sdpBypassAvgNs: Long,
    val hdpBypassAvgNs: Long,
    val wdpBypassAvgNs: Long,
    val sdpaCacheAvgNs: Long,
    val singleNoArAvgNs: Long,
    val singleWithArAvgNs: Long,
    val singleValue: Float,
    val extSdpAvgNs: Long,
    val apiSdpAvgNs: Long,
    val accumulatorChecksum: Float,
    val singleDp: SingleDpBenchmarkResult,
)

/**
 * EN Results from the Calculation Benchmark runner.
 *    Measures the average latency of mixed dimension resolutions (sdp, hdp, wdp, sdpa)
 *    in a single tight loop to simulate real-world usage patterns.
 * PT Resultados do runner de Benchmark de Cálculo.
 *    Mede a latência média de resoluções de dimensão mistas (sdp, hdp, wdp, sdpa)
 *    em um único loop para simular padrões de uso do mundo real.
 *
 * @param avgNsPerRes EN Average nanoseconds per resolution call. PT Média de nanossegundos por chamada.
 * @param totalOps    EN Total operations (calls) measured. PT Total de operações (chamadas) medidas.
 * @param throughput  EN Formatted string showing the call-type mix. PT String formatada mostrando o mix.
 */
data class CalculationBenchmarkResult(
    val avgNsPerRes: Long,
    val totalOps: Int,
    val throughput: String,
)

/**
 * EN Results from the Macrobenchmark runner.
 *    Measures real UI scroll performance across 1,000 items using wall-clock timing.
 *    Does NOT use measureNanoTime — uses currentTimeMillis start/end deltas.
 * PT Resultados do runner de Macrobenchmark.
 *    Mede a performance real de rolagem da UI em 1.000 itens usando tempo de relógio.
 *    NÃO usa measureNanoTime — usa deltas de start/end com currentTimeMillis.
 *
 * @param scrollDurationMs       EN Full scroll pass duration in ms. PT Duração da passagem de rolagem em ms.
 * @param itemsRendered          EN Number of items in the LazyColumn. PT Número de itens no LazyColumn.
 * @param estimatedCostPerItemUs EN Estimated rendering cost per item in µs. PT Custo estimado por item em µs.
 * @param estimatedFrames        EN Estimated frame count at 60fps. PT Contagem de frames estimada a 60fps.
 * @param notes                  EN Additional context or observations. PT Contexto ou observações adicionais.
 */
data class MacroBenchmarkResult(
    val scrollDurationMs: Long,
    val itemsRendered: Int,
    val estimatedCostPerItemUs: Float,
    val estimatedFrames: Int,
    val notes: String,
)

/**
 * EN Unified benchmark result container. All fields are nullable since the user may
 *    choose to run only Micro or only Macro.
 * PT Contêiner unificado de resultados de benchmark. Todos os campos são nulos pois o usuário
 *    pode optar por executar apenas Micro ou apenas Macro.
 *
 * @param calculation EN Calculation benchmark result, or null if not run. PT Resultado do benchmark de cálculo, ou null.
 * @param micro       EN Microbenchmark result, or null if not run. PT Resultado do microbenchmark, ou null.
 * @param macro       EN Macrobenchmark result, or null if not run. PT Resultado do macrobenchmark, ou null.
 */
data class BenchmarkResult(
    val calculation: CalculationBenchmarkResult? = null,
    val micro: MicroBenchmarkResult? = null,
    val macro: MacroBenchmarkResult? = null,
)

// ─── Formatting helpers ────────────────────────────────────────────────────────

/** EN Formats a nanosecond value into a readable string with appropriate unit. */
fun Long.formatNs(): String = when {
    this < 1_000L     -> "$this ns"
    this < 1_000_000L -> "${"%.1f".format(this / 1_000.0)} µs"
    else              -> "${"%.2f".format(this / 1_000_000.0)} ms"
}

/** EN Formats a float microsecond value with 2 decimal places. */
fun Float.formatUs(): String = "${"%.2f".format(this)} µs"