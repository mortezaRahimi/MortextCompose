package com.mortex.mortext.presentation.nav

sealed class NavRoute(val route:String) {
    data object Splash:NavRoute(route =Screen.SPLASH.name)
    data object Login:NavRoute(route =Screen.LOGIN.name)
    data object Main:NavRoute(route =Screen.MAIN.name)
}

enum class Screen {
    SPLASH , LOGIN , MAIN
}