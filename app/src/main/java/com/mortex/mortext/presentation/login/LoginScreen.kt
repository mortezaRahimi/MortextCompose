package com.mortex.mortext.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), modifier: Modifier = Modifier, nextScreen:()->Unit) {

    val email = remember {  mutableStateOf("")}
    val password = remember {  mutableStateOf("")}

    Column(modifier = modifier.fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        TextField(modifier = Modifier.fillMaxWidth() ,
            onValueChange = {_value->
                email.value = _value
            } ,
            value = email.value,
            label = { Text("Email") },
            maxLines = 1)

        HorizontalDivider(Modifier.height(20.dp))
        TextField(modifier = Modifier.fillMaxWidth(),
            onValueChange = {_value->
                password.value = _value
            } ,
            visualTransformation = PasswordVisualTransformation(),
            value = password.value,
            label = { Text("Password") },
            maxLines = 1)

        HorizontalDivider(Modifier.height(20.dp))

        ElevatedButton(onClick = nextScreen ,
            enabled = true,
            shape = RoundedCornerShape(8.dp),
            content = { Text("Login") }
        )
    }

}