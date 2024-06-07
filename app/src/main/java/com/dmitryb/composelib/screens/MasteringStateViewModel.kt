package com.dmitryb.composelib.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MasteringStateViewModel : ViewModel() {
    private val _state =
        MutableStateFlow<MasteringStateScreenModel>(
            MasteringStateScreenModel.Loaded(
                listOf(Settings.CheckBox("Option 1", false))
            )
        )

    val state: StateFlow<MasteringStateScreenModel>
        get() = _state.asStateFlow()

    fun onUserAction(userAction: UserAction) {
        Log.d("MasteringStateViewModel", "onUserAction: $userAction")
        // TODO(): Extract to Reducer
        val contentAction = userAction as? UserAction.Content ?: return
        _state.update { current ->
            when (current) {
                MasteringStateScreenModel.Initial -> current
                is MasteringStateScreenModel.Loaded -> reduce(current, contentAction)
                MasteringStateScreenModel.Loading -> current
            }
        }
    }

    private fun reduce(
        stateScreenModel: MasteringStateScreenModel.Loaded,
        action: UserAction.Content
    ): MasteringStateScreenModel {
        return when (action) {
            is UserAction.Content.CheckBoxClick -> {
                val index = stateScreenModel.settingsList
                    .indexOfFirst { it == action.state }
                    .takeIf { it != -1 }
                    ?: return stateScreenModel
                stateScreenModel.copy(
                    settingsList = stateScreenModel.settingsList
                        .toMutableList()
                        .also {
                            it[index] = action.state.copy(checked = action.newValue)
                        }.toList()
                )
            }
        }
    }
}
