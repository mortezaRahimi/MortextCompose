package com.mortex.mortext.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mortex.mortext.core.UiState
import com.mortex.mortext.domain.model.User
import com.mortex.mortext.domain.usecase.GetUsersUseCase
import com.mortex.mortext.presentation.main.state.UsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase):ViewModel() {

    private val _users = mutableStateOf(UsersState())
    val users: State<UsersState> get() = _users

    init {

        getUsersFromDb()

    }

    private fun getUsers(){
        getUsersUseCase.invoke().onEach {flow->
            when(flow){
                is UiState.Loading ->{
                    _users.value = UsersState(loading = true)
                }
                is UiState.Error -> {
                    _users.value = UsersState(loading = false , message = flow.message.toString())

                }
                is UiState.Success -> {
                    _users.value = UsersState(loading = false , users = flow.data)
                    _users.value.users?.forEach {
                        getUsersUseCase.addUser(it)
                    }
                }
            }
        }

            .launchIn(viewModelScope)
    }

    private fun getUsersFromDb(){
        getUsersUseCase.getAllFromDb().onEach {flow->
            when(flow){
                is UiState.Loading ->{
                    _users.value = UsersState(loading = true)
                }
                is UiState.Error -> {
                    _users.value = UsersState(loading = false , message = flow.message.toString())
                }
                is UiState.Success -> {
                    if(flow.data!!.isEmpty()){
                        getUsers()
                    }else{
                        _users.value = UsersState(loading = false , users = flow.data)
                    }

                }
            }
        }

            .launchIn(viewModelScope)
    }

}