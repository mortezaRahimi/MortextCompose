package com.mortex.mortext.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mortex.mortext.presentation.detail.DetailScreen
import com.mortex.mortext.presentation.login.LoginScreen
import com.mortex.mortext.presentation.main.MainScreen
import com.mortex.mortext.presentation.main.MainViewModel
import com.mortex.mortext.presentation.nav.NavRoute
import com.mortex.mortext.presentation.splash.SplashScreen
import com.mortex.mortext.ui.theme.MortextComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {

            MortextComposeTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    SharedTransitionLayout {

                        val navController = rememberNavController()
                        val viewModel :MainViewModel = hiltViewModel()

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
                                MainScreen(
                                    modifier = Modifier,
                                    innerPadding = innerPadding,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedContentScope = this@composable,
                                    viewModel = viewModel,
                                    nextScreen = {
                                        navController.navigate("detail/$it")
                                    })
                            }

                            composable(
                                "detail/{item}",
                                arguments = listOf(navArgument("item") { type = NavType.IntType })
                            ) { backStackEntry ->
                                val id = backStackEntry.arguments?.getInt("item")

                                DetailScreen(
                                    this@SharedTransitionLayout,
                                    this@composable,
                                    id = id!!,
                                    viewModel = viewModel,
                                    onBackPressed = { navController.navigateUp() }
                                )

                            }
                        }


                    }
                }
            }
        }
    }

}


