package com.dmitryb.composelib.screens

sealed class MasteringStateScreenModel {
    data object Initial : MasteringStateScreenModel()

    data object Loading : MasteringStateScreenModel()

    data class Loaded(val settingsList: List<Settings>) : MasteringStateScreenModel()
}

// TODO(): Add partitions to screen, several separate parts like groups of settings
sealed class Settings(open val title: String) {

    data class CheckBox(override val title: String, val checked: Boolean) : Settings(title)

    data class TextField(override val title: String, val text: String) : Settings(title)

    data class RadioGroup(override val title: String, val radioButtons: List<RadioButton>) : Settings(title)

    data class RadioButton(override val title: String, val checked: Boolean) : Settings(title)

}
