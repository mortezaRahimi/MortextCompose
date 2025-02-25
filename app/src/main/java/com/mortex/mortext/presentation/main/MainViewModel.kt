package com.mortex.mortext.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mortex.mortext.core.UiState
import com.mortex.mortext.domain.model.User
import com.mortex.mortext.domain.usecase.GetUsersUseCase
import com.mortex.mortext.presentation.event.UiEvent
import com.mortex.mortext.presentation.main.event.MainEvent
import com.mortex.mortext.presentation.main.state.UsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase) :
    ViewModel() {

    var usersState by mutableStateOf(UsersState())


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {

        viewModelScope.launch {
            usersState = UsersState(loading = true)
//            delay(3000)
            getUsersFromDb()
//            getUsers()
        }

    }

    fun onEvent(event: MainEvent) {

        when (event) {

            is MainEvent.ShowUrl -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.ShowUrl(event.url))
                }
            }

            else -> {}
        }
    }

     fun getUsers() {

        getUsersUseCase.invoke().onEach { flow ->
            when (flow) {
                is UiState.Loading -> {
                    usersState = UsersState(loading = true)
                }

                is UiState.Error -> {
                    usersState = UsersState(loading = false, message = flow.message.toString())

                }

                is UiState.Success -> {
                    usersState = UsersState(loading = false, users = flow.data!!)
                    usersState.users.forEach {
                        getUsersUseCase.addUser(it)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun getUsersFromDb() {
        getUsersUseCase.getAllFromDb().onEach { flow ->
            when (flow) {
                is UiState.Loading -> {
                    usersState = UsersState(loading = true)
                }

                is UiState.Error -> {
                    usersState = UsersState(loading = false, message = flow.message.toString())
                }

                is UiState.Success -> {
                    if (flow.data!!.isEmpty()) {
                        getUsers()
                    } else {
                        usersState = UsersState(loading = false, users = flow.data)
                    }

                }
            }
        }

            .launchIn(viewModelScope)
    }

}

class Car : Play {
    override fun make() {
        TODO("Not yet implemented")
    }

}

interface Play {
    fun make()
}


class Jok(private val name: String) {

    constructor(family: String, name: String) : this(name) {
        require(family.isNotBlank()) {

        }
    }

    fun greet() {
        println(name)
    }

}

fun main() {
    val name = Jok("")
    val name2 = Jok("", "")

    name.greet()
    name2.greet()

}