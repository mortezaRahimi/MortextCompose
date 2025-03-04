package com.mortex.mortext.presentation.detail

import android.widget.Toast
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mortex.mortext.domain.model.User
import com.mortex.mortext.presentation.event.UiEvent
import com.mortex.mortext.presentation.main.MainViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackPressed: () -> Unit,
    viewModel: MainViewModel,
    id: Int,
) {

    val context = LocalContext.current
    with(sharedTransitionScope) {

        val user = viewModel.usersState.users.find {
            it.id == id
        }

        LaunchedEffect(true) {
            viewModel.uiEvent.collect { event ->

                when (event) {
                    UiEvent.Loading -> TODO()
                    UiEvent.LogOut -> TODO()
                    is UiEvent.ShowToast -> Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_LONG
                    ).show()

                    is UiEvent.ShowUrl -> TODO()
                }

            }
        }

        val weather by produceState(initialValue = "Loading...", "Arnhem") {
            value = viewModel.fetchWeather()
        }

        Column(
            Modifier
                .fillMaxSize()
                .clickable {
                    onBackPressed()
                }
        ) {


            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user?.picture)
                    .crossfade(true)
                    .build(),
                contentDescription = user?.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "image-$id"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .aspectRatio(1f)
                    .fillMaxWidth()
            )
            Text(
                user?.name ?: "", fontSize = 18.sp,
                modifier =
                Modifier
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "text-$id"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text("$weather weather in Arnhem")

        }
    }
}

interface Shape {
    fun area(): Int
}

class Circle(val sh: Int) : Shape {
    override fun area(): Int {
        return sh * sh
    }
}

class Square(val sh: Int) : Shape {
    override fun area(): Int {
        return sh * sh
    }
}

fun calculateArea(shape: Shape) {
    val sq = Square(3)
    val cir = Circle(2)

    sq.area()
}