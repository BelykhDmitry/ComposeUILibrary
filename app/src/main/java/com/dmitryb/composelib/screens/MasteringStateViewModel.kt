package com.dmitryb.composelib.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MasteringStateViewModel : ViewModel() {
    private val _state =
        MutableStateFlow<MasteringStateScreenModel>(MasteringStateScreenModel.Initial)

    val state: StateFlow<MasteringStateScreenModel>
        get() = _state.asStateFlow()

    fun onUserAction(userAction: MasteringStateScreenAction) {

    }
}
