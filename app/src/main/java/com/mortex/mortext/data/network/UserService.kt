package com.mortex.mortext.data.network

import androidx.lifecycle.MutableLiveData
import com.mortex.mortext.domain.model.User
import retrofit2.http.GET

interface UserService {

    @GET("/users")
    suspend fun getUsers(): List<User>

}