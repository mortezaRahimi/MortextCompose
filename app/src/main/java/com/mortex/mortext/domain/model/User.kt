package com.mortex.mortext.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
    @PrimaryKey @SerializedName("url") val userUrl: String,
    @SerializedName("login") val name: String,
    @SerializedName("avatar_url") val picture: String

)

