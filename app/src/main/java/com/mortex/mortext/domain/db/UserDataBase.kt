package com.mortex.mortext.domain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mortex.mortext.domain.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao() : UserDao
}