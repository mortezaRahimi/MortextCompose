package com.mortex.mortext.presentation.main

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.mortex.mortext.presentation.event.UiEvent
import com.mortex.mortext.presentation.main.components.UserItem
import com.mortex.mortext.presentation.main.event.MainEvent
import com.mortex.mortext.ui.theme.Purple40
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier,
    innerPadding: PaddingValues,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    nextScreen: (Int) -> Unit
) {

    var state = viewModel.usersState
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.Loading -> {
//                        viewModel.onEvent(MainEvent.Loading(true))
                }

                UiEvent.LogOut -> {

                }

                is UiEvent.ShowToast -> {

                }

                is UiEvent.ShowUrl -> {
                    uriHandler.openUri(event.url)
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(color = Purple40),
        contentAlignment = Alignment.Center,
    ) {

        CircularProgressIndicator(modifier = Modifier.alpha(if (state.loading) 1.0f else 0.0f))
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            columns = GridCells.Fixed(3)
        ) {
            items(state.users.size) { i ->
                state.users.let { list ->

                    val item = list[i]
                    UserItem(
                        modifier,
                        sharedTransitionScope,
                        animatedContentScope,
                        user = item,
                        onclick = { nextScreen(it) })
                }

            }
        }
    }
}


@Composable
fun currentTime(): String {
    return System.currentTimeMillis().toString()
}