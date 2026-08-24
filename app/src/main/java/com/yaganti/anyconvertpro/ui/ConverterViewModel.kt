package com.yaganti.anyconvertpro.ui

import androidx.lifecycle.ViewModel
import com.yaganti.anyconvertpro.domain.ConversionUnit
import com.yaganti.anyconvertpro.domain.UnitCatalog
import com.yaganti.anyconvertpro.domain.UnitCategory
import com.yaganti.anyconvertpro.domain.UnitConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ConverterUiState(
    val category: UnitCategory = UnitCategory.LENGTH,
    val units: List<ConversionUnit> = UnitCatalog.unitsFor(UnitCategory.LENGTH),
    val fromUnitId: String = "m",
    val toUnitId: String = "ft",
    val input: String = "1",
    val result: String = "",
    val inputError: Boolean = false,
)

class ConverterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ConverterUiState().withConvertedResult())
    val uiState: StateFlow<ConverterUiState> = _uiState.asStateFlow()

    fun onCategorySelected(category: UnitCategory) {
        val units = UnitCatalog.unitsFor(category)
        _uiState.update {
            it.copy(
                category = category,
                units = units,
                fromUnitId = units.first().id,
                toUnitId = units.getOrElse(1) { units.first() }.id,
            ).withConvertedResult()
        }
    }

    fun onFromUnitSelected(unitId: String) {
        _uiState.update { state ->
            val swappedTo = if (unitId == state.toUnitId) state.fromUnitId else state.toUnitId
            state.copy(fromUnitId = unitId, toUnitId = swappedTo).withConvertedResult()
        }
    }

    fun onToUnitSelected(unitId: String) {
        _uiState.update { state ->
            val swappedFrom = if (unitId == state.fromUnitId) state.toUnitId else state.fromUnitId
            state.copy(fromUnitId = swappedFrom, toUnitId = unitId).withConvertedResult()
        }
    }

    fun onInputChanged(input: String) {
        _uiState.update { it.copy(input = input).withConvertedResult() }
    }

    fun swapUnits() {
        _uiState.update {
            it.copy(fromUnitId = it.toUnitId, toUnitId = it.fromUnitId).withConvertedResult()
        }
    }

    private fun ConverterUiState.withConvertedResult(): ConverterUiState {
        val parsed = input.trim().replace(',', '.').toDoubleOrNull()
        if (input.isBlank()) {
            return copy(result = "", inputError = false)
        }
        if (parsed == null) {
            return copy(result = "", inputError = true)
        }
        val from = UnitCatalog.requireUnit(fromUnitId)
        val to = UnitCatalog.requireUnit(toUnitId)
        return copy(result = UnitConverter.format(UnitConverter.convert(parsed, from, to)), inputError = false)
    }
}
