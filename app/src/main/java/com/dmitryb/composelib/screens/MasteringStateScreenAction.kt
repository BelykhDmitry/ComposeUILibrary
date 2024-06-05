package com.dmitryb.composelib.screens

sealed class MasteringStateScreenAction {
    data object AccountClick : MasteringStateScreenAction()

    data object CheckClick : MasteringStateScreenAction()
}
