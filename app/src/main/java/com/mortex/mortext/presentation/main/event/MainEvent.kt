package com.mortex.mortext.presentation.main.event

sealed class MainEvent {

    data class ShowUrl(val url:String): MainEvent()
}