package com.dmitryb.composelib.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Screen for recomposition optimisation practice.
 * There will be lots of settings for user input, such as TextFields, Switches, Toggles, Drop-down
 * lists.
 * steps: create a dummy screen with settings without optimisations, then sequentially optimise
 * screen model, local state of elements and check an impact.
 */
@Composable
fun MasteringStateScreen(
    modifier: Modifier = Modifier,
    viewModel: MasteringStateViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    MasteringStateScreenContent(state, viewModel::onUserAction, modifier)
}

@Composable
private fun MasteringStateScreenContent(
    stateScreenModel: MasteringStateScreenModel,
    onUserAction: (MasteringStateScreenAction) -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO(): Implement settings screen
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = { onUserAction(MasteringStateScreenAction.AccountClick) }) {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
                }
                IconButton(onClick = { onUserAction(MasteringStateScreenAction.CheckClick) }) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (stateScreenModel) {
                MasteringStateScreenModel.Initial -> TODO()
                is MasteringStateScreenModel.Loaded -> SettingsList(
                    stateScreenModel = stateScreenModel,
                    onUserAction = onUserAction
                )
                MasteringStateScreenModel.Loading -> TODO()
            }
        }
    }
}

@Composable
private fun SettingsList(
    stateScreenModel: MasteringStateScreenModel.Loaded,
    onUserAction: (MasteringStateScreenAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(stateScreenModel.settingsList) { settingsItem ->
            when (settingsItem) {
                is Settings.CheckBox -> TODO()
                is Settings.RadioButton -> TODO()
                is Settings.RadioGroup -> TODO()
                is Settings.TextField -> TODO()
            }
        }
    }
}

@Preview
@Composable
fun MasteringStateScreenPreview() {

}
