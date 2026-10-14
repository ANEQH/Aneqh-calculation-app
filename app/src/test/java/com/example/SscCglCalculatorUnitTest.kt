package com.example

import com.example.data.repository.CalculationSolvers
import com.example.data.repository.SscCglSolvers
import com.example.ui.screens.calculateBasic
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SscCglCalculatorUnitTest {

    @Test
    fun testCalculatorBasicArithmetic() {
        val res = calculateBasic("12 + 8 * 2")
        assertEquals(28.0, res, 0.001)
    }

    @Test
    fun testCalculatorParentheses() {
        val res = calculateBasic("(12 + 8) * 2")
        assertEquals(40.0, res, 0.001)
    }

    @Test
    fun testPercentageHubModes() {
        // Mode 1: 20% of 100
        val m1 = SscCglSolvers.solvePercentageHub(1, 20.0, 100.0)
        assertEquals("20", m1.finalAnswer)

        // Mode 2: 20 is what % of 80
        val m2 = SscCglSolvers.solvePercentageHub(2, 20.0, 80.0)
        assertEquals("25%", m2.finalAnswer)

        // Mode 3: 100 increased by 20%
        val m3 = SscCglSolvers.solvePercentageHub(3, 20.0, 100.0)
        assertEquals("120", m3.finalAnswer)

        // Mode 4: 100 decreased by 20%
        val m4 = SscCglSolvers.solvePercentageHub(4, 20.0, 100.0)
        assertEquals("80", m4.finalAnswer)

        // Mode 5: Successive increase 20% and 10%
        val m5 = SscCglSolvers.solvePercentageHub(5, 20.0, 10.0)
        assertEquals("32%", m5.finalAnswer)

        // Mode 6: Successive discount 20% and 10%
        val m6 = SscCglSolvers.solvePercentageHub(6, 20.0, 10.0)
        assertEquals("28% Discount", m6.finalAnswer)
    }

    @Test
    fun testSscSolversSpeedAndBoat() {
        val speedRes = CalculationSolvers.solveSpeedDistance(60.0, 40.0, true)
        assertTrue(speedRes.finalAnswer.contains("48.00 km/h"))

        val boatRes = CalculationSolvers.solveBoatStream(18.0, 12.0)
        assertTrue(boatRes.finalAnswer.contains("Boat: 15.00 km/h"))
        assertTrue(boatRes.finalAnswer.contains("Current: 3.00 km/h"))
    }
}
