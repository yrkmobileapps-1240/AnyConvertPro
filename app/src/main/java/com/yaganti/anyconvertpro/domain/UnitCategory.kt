package com.yaganti.anyconvertpro.domain

import androidx.annotation.StringRes
import com.yaganti.anyconvertpro.R

enum class UnitCategory(
    @StringRes val titleRes: Int,
    @StringRes val lessonRes: Int,
) {
    LENGTH(R.string.category_length, R.string.lesson_length),
    MASS(R.string.category_mass, R.string.lesson_mass),
    TEMPERATURE(R.string.category_temperature, R.string.lesson_temperature),
    VOLUME(R.string.category_volume, R.string.lesson_volume),
    AREA(R.string.category_area, R.string.lesson_area),
    SPEED(R.string.category_speed, R.string.lesson_speed),
}
