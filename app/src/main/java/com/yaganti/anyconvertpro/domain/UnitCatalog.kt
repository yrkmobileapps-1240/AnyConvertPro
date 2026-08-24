package com.yaganti.anyconvertpro.domain

import com.yaganti.anyconvertpro.R

object UnitCatalog {
    private fun linear(
        id: String,
        category: UnitCategory,
        nameRes: Int,
        symbol: String,
        toBaseFactor: Double,
    ) = ConversionUnit(
        id = id,
        category = category,
        nameRes = nameRes,
        symbol = symbol,
        toBase = { it * toBaseFactor },
        fromBase = { it / toBaseFactor },
    )

    val units: List<ConversionUnit> = listOf(
        linear("m", UnitCategory.LENGTH, R.string.unit_meter, "m", 1.0),
        linear("km", UnitCategory.LENGTH, R.string.unit_kilometer, "km", 1_000.0),
        linear("cm", UnitCategory.LENGTH, R.string.unit_centimeter, "cm", 0.01),
        linear("mm", UnitCategory.LENGTH, R.string.unit_millimeter, "mm", 0.001),
        linear("mi", UnitCategory.LENGTH, R.string.unit_mile, "mi", 1_609.344),
        linear("yd", UnitCategory.LENGTH, R.string.unit_yard, "yd", 0.9144),
        linear("ft", UnitCategory.LENGTH, R.string.unit_foot, "ft", 0.3048),
        linear("in", UnitCategory.LENGTH, R.string.unit_inch, "in", 0.0254),

        linear("kg", UnitCategory.MASS, R.string.unit_kilogram, "kg", 1.0),
        linear("g", UnitCategory.MASS, R.string.unit_gram, "g", 0.001),
        linear("mg", UnitCategory.MASS, R.string.unit_milligram, "mg", 0.000001),
        linear("lb", UnitCategory.MASS, R.string.unit_pound, "lb", 0.45359237),
        linear("oz", UnitCategory.MASS, R.string.unit_ounce, "oz", 0.028349523125),
        linear("t", UnitCategory.MASS, R.string.unit_tonne, "t", 1_000.0),

        ConversionUnit(
            id = "c",
            category = UnitCategory.TEMPERATURE,
            nameRes = R.string.unit_celsius,
            symbol = "°C",
            toBase = { it },
            fromBase = { it },
        ),
        ConversionUnit(
            id = "f",
            category = UnitCategory.TEMPERATURE,
            nameRes = R.string.unit_fahrenheit,
            symbol = "°F",
            toBase = { (it - 32.0) * 5.0 / 9.0 },
            fromBase = { it * 9.0 / 5.0 + 32.0 },
        ),
        ConversionUnit(
            id = "k",
            category = UnitCategory.TEMPERATURE,
            nameRes = R.string.unit_kelvin,
            symbol = "K",
            toBase = { it - 273.15 },
            fromBase = { it + 273.15 },
        ),

        linear("l", UnitCategory.VOLUME, R.string.unit_liter, "L", 1.0),
        linear("ml", UnitCategory.VOLUME, R.string.unit_milliliter, "mL", 0.001),
        linear("m3", UnitCategory.VOLUME, R.string.unit_cubic_meter, "m³", 1_000.0),
        linear("gal", UnitCategory.VOLUME, R.string.unit_gallon, "gal", 3.785411784),
        linear("qt", UnitCategory.VOLUME, R.string.unit_quart, "qt", 0.946352946),
        linear("cup", UnitCategory.VOLUME, R.string.unit_cup, "cup", 0.2365882365),
        linear("floz", UnitCategory.VOLUME, R.string.unit_fluid_ounce, "fl oz", 0.0295735295625),

        linear("m2", UnitCategory.AREA, R.string.unit_square_meter, "m²", 1.0),
        linear("km2", UnitCategory.AREA, R.string.unit_square_kilometer, "km²", 1_000_000.0),
        linear("ha", UnitCategory.AREA, R.string.unit_hectare, "ha", 10_000.0),
        linear("acre", UnitCategory.AREA, R.string.unit_acre, "ac", 4_046.8564224),
        linear("ft2", UnitCategory.AREA, R.string.unit_square_foot, "ft²", 0.09290304),
        linear("in2", UnitCategory.AREA, R.string.unit_square_inch, "in²", 0.00064516),

        linear("mps", UnitCategory.SPEED, R.string.unit_meters_per_second, "m/s", 1.0),
        linear("kmh", UnitCategory.SPEED, R.string.unit_kilometers_per_hour, "km/h", 1_000.0 / 3_600.0),
        linear("mph", UnitCategory.SPEED, R.string.unit_miles_per_hour, "mph", 1_609.344 / 3_600.0),
        linear("kn", UnitCategory.SPEED, R.string.unit_knot, "kn", 1_852.0 / 3_600.0),
    )

    fun unitsFor(category: UnitCategory): List<ConversionUnit> =
        units.filter { it.category == category }

    fun requireUnit(id: String): ConversionUnit =
        units.first { it.id == id }
}
