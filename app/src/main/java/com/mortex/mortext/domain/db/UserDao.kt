package com.mortex.mortext.domain.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mortex.mortext.domain.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE userUrl = :url")
    suspend fun getUser(url: String): User?

    @Query("Select * FROM user")
    suspend fun getAllUsers(): List<User>
}