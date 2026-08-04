/**
 * Process-wide cache for [Resources.getIdentifier] lookups of SDP/HDP/WDP dimen names.
 *
 * Resource IDs are stable for the process lifetime. Configuration changes affect the value
 * returned by [Resources.getDimension], not the ID.
 */
package com.appdimens.sdps.core

import android.annotation.SuppressLint
import android.content.res.Resources
import androidx.annotation.VisibleForTesting
import java.util.concurrent.ConcurrentHashMap

object DimenResourceIdCache {
    private const val DIMEN_TYPE = "dimen"

    private val idByPackageAndName = ConcurrentHashMap<String, Int>(256)

    @SuppressLint("DiscouragedApi")
    fun getOrResolve(resources: Resources, packageName: String, dimenName: String): Int {
        val key = buildKey(packageName, dimenName)
        idByPackageAndName[key]?.let { return it }
        val id = resources.getIdentifier(dimenName, DIMEN_TYPE, packageName)
        val raced = idByPackageAndName.putIfAbsent(key, id)
        return raced ?: id
    }

    @VisibleForTesting
    internal fun resetForTestsOnly() {
        idByPackageAndName.clear()
    }

    @VisibleForTesting
    internal fun cachedSizeForTestsOnly(): Int = idByPackageAndName.size

    private fun buildKey(packageName: String, dimenName: String): String =
        "$packageName\u0000$dimenName"
}
