package com.yaganti.anyconvertpro.domain

import androidx.annotation.StringRes

data class ConversionUnit(
    val id: String,
    val category: UnitCategory,
    @StringRes val nameRes: Int,
    val symbol: String,
    val toBase: (Double) -> Double,
    val fromBase: (Double) -> Double,
)
