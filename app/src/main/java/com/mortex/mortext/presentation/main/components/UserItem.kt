package com.mortex.mortext.presentation.main.components


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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mortex.mortext.domain.model.User

@Composable
fun UserItem(modifier: Modifier = Modifier, user: User) {

    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .padding(16.dp)
            .clickable {   uriHandler.openUri(user.userUrl) },
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
                .clip(CircleShape)
                .width(100.dp)


        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = user.name, style = TextStyle(color = Color.White))

    }
}