package com.mortex.mortext.data.repository

import com.mortex.mortext.data.network.UserService
import com.mortex.mortext.domain.db.UserDao
import com.mortex.mortext.domain.model.User
import com.mortex.mortext.domain.repository.UsersRepository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val userService: UserService, private val userDao: UserDao) : UsersRepository {

    override suspend fun getUsers(): List<User> {
        return userService.getUsers()
    }

    override suspend fun getUsersFromDB(): List<User> {
      return userDao.getAllUsers()
    }

    override suspend fun addUserToDb(user: User) {
        userDao.insertUser(user)
    }
}