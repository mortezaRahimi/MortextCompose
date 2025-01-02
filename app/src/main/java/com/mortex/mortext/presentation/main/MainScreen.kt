package com.mortex.mortext.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mortex.mortext.presentation.main.components.UserItem
import com.mortex.mortext.ui.theme.Purple40

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    modifier: Modifier,
    innerPadding: PaddingValues
) {
    val state = viewModel.users.value

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(color = Purple40),
        contentAlignment = Alignment.Center,
    ) {

        LazyColumn(modifier = Modifier.fillMaxSize() ,
            horizontalAlignment = Alignment.CenterHorizontally) {
           items( state.users?.size?: 0 ){item->
               state.users?.let{
                   UserItem(modifier ,user = it[item])
               }

           }
        }
    }
}