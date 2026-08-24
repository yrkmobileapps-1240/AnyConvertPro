package com.yaganti.anyconvertpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yaganti.anyconvertpro.ui.ConverterViewModel
import com.yaganti.anyconvertpro.ui.UnitConverterScreen
import com.yaganti.anyconvertpro.ui.theme.AnyConvertProTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnyConvertProTheme {
                val viewModel: ConverterViewModel = viewModel()
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                            ),
                        )
                    },
                ) { innerPadding ->
                    UnitConverterScreen(
                        state = state,
                        onCategorySelected = viewModel::onCategorySelected,
                        onFromUnitSelected = viewModel::onFromUnitSelected,
                        onToUnitSelected = viewModel::onToUnitSelected,
                        onInputChanged = viewModel::onInputChanged,
                        onSwapUnits = viewModel::swapUnits,
                        contentPadding = innerPadding,
                    )
                }
            }
        }
    }
}
