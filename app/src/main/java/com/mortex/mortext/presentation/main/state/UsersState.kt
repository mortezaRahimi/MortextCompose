package com.mortex.mortext.presentation.main.state


import com.mortex.mortext.domain.model.User

data class UsersState(
    val users: List<User> = emptyList(),
    val loading:Boolean  = true,
    val message: String ="",
    val counter:Int = 0,
)
