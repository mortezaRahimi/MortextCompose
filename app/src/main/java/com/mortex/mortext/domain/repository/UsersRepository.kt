package com.mortex.mortext.domain.repository

import androidx.lifecycle.MutableLiveData
import com.mortex.mortext.domain.model.User

interface UsersRepository {

    suspend fun getUsers(): List<User>

    suspend fun getUsersFromDB(): List<User>

    suspend fun addUserToDb(user:User)
}