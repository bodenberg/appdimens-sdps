package com.appdimens.sdps

import com.appdimens.sdps.code.DimenPhysicalUnits.cmToInch
import com.appdimens.sdps.code.DimenPhysicalUnits.cmToMm
import com.appdimens.sdps.code.DimenPhysicalUnits.inchToCm
import com.appdimens.sdps.code.DimenPhysicalUnits.inchToMm
import com.appdimens.sdps.code.DimenPhysicalUnits.mmToCm
import com.appdimens.sdps.code.DimenPhysicalUnits.mmToInch
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Host unit tests for pure physical unit conversions (no Android runtime required).
 */
class PhysicalUnitsAndInverterUnitTest {

    @Test
    fun physicalUnitChain_roundTrips() {
        assertEquals(1.0f, 10f.mmToCm(), 0.0001f)
        assertEquals(25.4f, 1f.inchToMm(), 0.0001f)
        assertEquals(2.54f, 1f.inchToCm(), 0.0001f)
        assertEquals(10f, 1f.cmToMm(), 0.0001f)
        assertEquals(1f, 25.4f.mmToInch(), 0.0001f)
        assertEquals(1f, 2.54f.cmToInch(), 0.0001f)
    }
}
