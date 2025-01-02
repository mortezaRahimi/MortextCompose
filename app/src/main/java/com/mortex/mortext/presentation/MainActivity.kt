package com.mortex.mortext.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mortex.mortext.presentation.login.LoginScreen
import com.mortex.mortext.presentation.main.MainScreen
import com.mortex.mortext.presentation.nav.NavRoute
import com.mortex.mortext.presentation.splash.SplashScreen
import com.mortex.mortext.ui.theme.MortextComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {

            MortextComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(navController, startDestination = "Splash") {

                        composable(NavRoute.Splash.route) {
                            SplashScreen(modifier = Modifier,
                                paddingValues = innerPadding,
                                nextScreen = { navController.navigate(NavRoute.Login.route) })
                        }

                        composable(NavRoute.Login.route) {
                            LoginScreen(modifier = Modifier,
                                nextScreen = { navController.navigate(NavRoute.Main.route) })
                        }

                        composable(NavRoute.Main.route) {
                            MainScreen(modifier = Modifier, innerPadding = innerPadding)
                        }
                    }


                }
            }
        }
    }
}


