@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.mortex.mortext.presentation.main.components


import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mortex.mortext.domain.model.User


@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    user: User,
    onclick: (Int) -> Unit
) {

    with(sharedTransitionScope) {

        Column(
            modifier = modifier
                .padding(16.dp)
                .clickable { onclick(user.id) },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.picture)
                    .crossfade(true)
                    .build(),
                contentDescription = user.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "image-${user.id}"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .clip(CircleShape)
                    .width(100.dp)
                    .height(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = user.name, fontSize = 18.sp,
                modifier = Modifier.sharedElement(
                    sharedTransitionScope.rememberSharedContentState(key = "text-${user.id}"),
                    animatedVisibilityScope = animatedContentScope,
                ),
                style = TextStyle(color = Color.White)
            )

        }
    }


}