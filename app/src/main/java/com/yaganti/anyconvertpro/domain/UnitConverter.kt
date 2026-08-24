package com.yaganti.anyconvertpro.domain

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.abs

object UnitConverter {
    private val scientific = DecimalFormat("0.######E0", DecimalFormatSymbols(Locale.US))
    private val standard = DecimalFormat("0.########", DecimalFormatSymbols(Locale.US))

    fun convert(value: Double, from: ConversionUnit, to: ConversionUnit): Double {
        require(from.category == to.category) {
            "Cannot convert ${from.category} to ${to.category}"
        }
        return to.fromBase(from.toBase(value))
    }

    fun format(value: Double): String {
        if (!value.isFinite()) return "—"
        if (value == 0.0) return "0"
        val formatter = if (abs(value) >= 1_000_000 || abs(value) < 0.0001) scientific else standard
        return formatter.format(value)
    }
}
