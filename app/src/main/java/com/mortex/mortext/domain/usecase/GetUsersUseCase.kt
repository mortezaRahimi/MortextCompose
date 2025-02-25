package com.mortex.mortext.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.mortex.mortext.core.UiState
import com.mortex.mortext.data.repository.RepositoryImpl
import com.mortex.mortext.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val impl: RepositoryImpl) {

     fun addUser(user: User){
         CoroutineScope(Dispatchers.Main).launch {
            impl.addUserToDb(user)
         }
    }

     fun getAllFromDb(): Flow<UiState<List<User>>> = flow<UiState<List<User>>> {
        emit(UiState.Loading())
        try {
            emit(UiState.Success(data = impl.getUsersFromDB()))
        } catch (e: Exception) {
            emit(UiState.Error(message = e.message.toString()))
        }

    }.flowOn(Dispatchers.IO)

    operator fun invoke(): Flow<UiState<List<User>>> = flow {
        emit(UiState.Loading())
        try {
            emit(UiState.Success(data = impl.getUsers()))
        } catch (e: Exception) {
            emit(UiState.Error(message = e.message.toString()))
        }
    }.flowOn(Dispatchers.Default)
}