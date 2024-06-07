package com.dmitryb.composelib.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dmitryb.composelib.ui.theme.ComposeUILibraryTheme

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
    onUserAction: (UserAction) -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO(): Implement settings screen
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = { onUserAction(UserAction.AccountOptionClick) }) {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
                }
                IconButton(onClick = { onUserAction(UserAction.CheckOptionClick) }) {
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
                    onUserAction = onUserAction,
                    modifier = Modifier.fillMaxSize()
                )

                MasteringStateScreenModel.Loading -> TODO()
            }
        }
    }
}

@Composable
private fun SettingsList(
    stateScreenModel: MasteringStateScreenModel.Loaded,
    onUserAction: (UserAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(8.dp)) {
        items(stateScreenModel.settingsList) { settingsItem ->
            when (settingsItem) {
                is Settings.CheckBox -> SettingsCheckBox(
                    state = settingsItem,
                    onCheckedChange = { currentModel, value ->
                        onUserAction(
                            UserAction.Content.CheckBoxClick(
                                currentModel,
                                value
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                is Settings.RadioButton -> TODO()
                is Settings.RadioGroup -> TODO()
                is Settings.TextField -> TODO()
            }
        }
    }
}

@Composable
private fun SettingsCheckBox(
    state: Settings.CheckBox,
    onCheckedChange: (Settings.CheckBox, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = state.title, Modifier.weight(1f))
        Checkbox(
            checked = state.checked,
            onCheckedChange = { onCheckedChange(state, it) },
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize()
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SettingsCheckBoxPreview() {
    ComposeUILibraryTheme {
        SettingsCheckBox(
            Settings.CheckBox("Option 1", true),
            { _, _ -> },
            Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun MasteringStateScreenContentPreview() {
    ComposeUILibraryTheme {
        MasteringStateScreenContent(stateScreenModel = MasteringStateScreenModel.Loaded(
            listOf(Settings.CheckBox("Option 1", false))
        ), {})
    }
}
