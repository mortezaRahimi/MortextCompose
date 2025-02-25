package com.mortex.mortext.presentation.event

sealed class UiEvent {
    data class ShowToast(val message:String): UiEvent()
    data object LogOut : UiEvent()
    data object Loading : UiEvent()
    data class ShowUrl(val url:String):UiEvent()
}