package com.mortex.mortext.presentation.splash

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SplashScreen(modifier: Modifier,
                 paddingValues: PaddingValues,
                 nextScreen: ()->Unit) {
    Box(modifier = modifier.fillMaxSize()
        .padding(paddingValues.calculateTopPadding()),
        contentAlignment = Alignment.Center,
    ){
        Text(
            "SPLASH SCREEN",
            modifier = Modifier
                .align(Alignment.Center)
                .clickable(onClick = nextScreen)

            , style = MaterialTheme.typography.headlineLarge
        )
    }


}

