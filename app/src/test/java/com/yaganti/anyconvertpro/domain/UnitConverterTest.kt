package com.yaganti.anyconvertpro.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UnitConverterTest {
    private val delta = 1e-9

    @Test
    fun metersToFeet() {
        val result = UnitConverter.convert(
            1.0,
            UnitCatalog.requireUnit("m"),
            UnitCatalog.requireUnit("ft"),
        )
        assertEquals(3.280839895013123, result, delta)
    }

    @Test
    fun milesToKilometers() {
        val result = UnitConverter.convert(
            1.0,
            UnitCatalog.requireUnit("mi"),
            UnitCatalog.requireUnit("km"),
        )
        assertEquals(1.609344, result, delta)
    }

    @Test
    fun celsiusToFahrenheit() {
        val boiling = UnitConverter.convert(
            100.0,
            UnitCatalog.requireUnit("c"),
            UnitCatalog.requireUnit("f"),
        )
        assertEquals(212.0, boiling, delta)
    }

    @Test
    fun fahrenheitToKelvin() {
        val result = UnitConverter.convert(
            32.0,
            UnitCatalog.requireUnit("f"),
            UnitCatalog.requireUnit("k"),
        )
        assertEquals(273.15, result, delta)
    }

    @Test
    fun kilogramsToPounds() {
        val result = UnitConverter.convert(
            1.0,
            UnitCatalog.requireUnit("kg"),
            UnitCatalog.requireUnit("lb"),
        )
        assertEquals(2.2046226218487757, result, delta)
    }

    @Test
    fun litersToGallons() {
        val result = UnitConverter.convert(
            3.785411784,
            UnitCatalog.requireUnit("l"),
            UnitCatalog.requireUnit("gal"),
        )
        assertEquals(1.0, result, delta)
    }

    @Test
    fun kilometersPerHourToMetersPerSecond() {
        val result = UnitConverter.convert(
            36.0,
            UnitCatalog.requireUnit("kmh"),
            UnitCatalog.requireUnit("mps"),
        )
        assertEquals(10.0, result, delta)
    }

    @Test
    fun acresToSquareMeters() {
        val result = UnitConverter.convert(
            1.0,
            UnitCatalog.requireUnit("acre"),
            UnitCatalog.requireUnit("m2"),
        )
        assertEquals(4046.8564224, result, delta)
    }

    @Test
    fun identityConversion() {
        val result = UnitConverter.convert(
            42.5,
            UnitCatalog.requireUnit("cm"),
            UnitCatalog.requireUnit("cm"),
        )
        assertEquals(42.5, result, delta)
    }

    @Test
    fun formatZeroAndLargeValues() {
        assertEquals("0", UnitConverter.format(0.0))
        assertTrue(UnitConverter.format(1_000_000.0).contains("E"))
    }
}
