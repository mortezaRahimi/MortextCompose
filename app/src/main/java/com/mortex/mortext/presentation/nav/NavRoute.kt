package com.mortex.mortext.presentation.nav

sealed class NavRoute(val route: String) {
    data object Splash : NavRoute(route = Screen.SPLASH.name)
    data object Login : NavRoute(route = Screen.LOGIN.name)
    data object Main : NavRoute(route = Screen.MAIN.name)
    data object Detail : NavRoute(route = Screen.DETAIL.name)
}

enum class Screen {
    SPLASH, LOGIN, MAIN, DETAIL
}