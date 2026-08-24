package com.yaganti.anyconvertpro.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.yaganti.anyconvertpro.R
import com.yaganti.anyconvertpro.domain.ConversionUnit
import com.yaganti.anyconvertpro.domain.UnitCategory

@Composable
fun UnitConverterScreen(
    state: ConverterUiState,
    onCategorySelected: (UnitCategory) -> Unit,
    onFromUnitSelected: (String) -> Unit,
    onToUnitSelected: (String) -> Unit,
    onInputChanged: (String) -> Unit,
    onSwapUnits: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(R.string.converter_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        CategoryRow(selected = state.category, onSelected = onCategorySelected)
        ValueField(
            value = state.input,
            isError = state.inputError,
            onValueChange = onInputChanged,
        )
        UnitDropdown(
            label = stringResource(R.string.from_unit),
            units = state.units,
            selectedId = state.fromUnitId,
            onSelected = onFromUnitSelected,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            IconButton(onClick = onSwapUnits) {
                Icon(
                    imageVector = Icons.Filled.SwapVert,
                    contentDescription = stringResource(R.string.swap_units),
                )
            }
        }
        UnitDropdown(
            label = stringResource(R.string.to_unit),
            units = state.units,
            selectedId = state.toUnitId,
            onSelected = onToUnitSelected,
        )
        ResultCard(result = state.result, toUnit = state.units.first { it.id == state.toUnitId })
        LessonCard(category = state.category)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategoryRow(
    selected: UnitCategory,
    onSelected: (UnitCategory) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        UnitCategory.entries.forEach { category ->
            FilterChip(
                selected = category == selected,
                onClick = { onSelected(category) },
                label = { Text(stringResource(category.titleRes)) },
            )
        }
    }
}

@Composable
private fun ValueField(
    value: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.input_value)) },
        isError = isError,
        supportingText = if (isError) {
            { Text(stringResource(R.string.invalid_number)) }
        } else {
            null
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UnitDropdown(
    label: String,
    units: List<ConversionUnit>,
    selectedId: String,
    onSelected: (String) -> Unit,
) {
    var expanded by remember(selectedId, units) { mutableStateOf(false) }
    val selected = units.first { it.id == selectedId }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
            readOnly = true,
            value = "${stringResource(selected.nameRes)} (${selected.symbol})",
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            units.forEach { unit ->
                DropdownMenuItem(
                    text = { Text("${stringResource(unit.nameRes)} (${unit.symbol})") },
                    onClick = {
                        onSelected(unit.id)
                        expanded = false
                    },
                )
            }
        }
    }
}

@Composable
private fun ResultCard(result: String, toUnit: ConversionUnit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = stringResource(R.string.result_label),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = result.ifBlank { "—" },
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = toUnit.symbol,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
    }
}

@Composable
private fun LessonCard(category: UnitCategory) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(R.string.learn_title),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = stringResource(category.lessonRes),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
