package com.dmitryb.composelib.screens

sealed class UserAction {
    data object AccountOptionClick : UserAction()

    data object CheckOptionClick : UserAction()

    sealed class Content : UserAction() {
        data class CheckBoxClick(
            val state: Settings.CheckBox,
            val newValue: Boolean
        ) : Content()
    }
}
