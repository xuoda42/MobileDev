package com.example.list.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.UUID


@Entity(
    tableName = "users",
    indices = [Index("id")]
)
data class User(
    @SerializedName("id") @PrimaryKey val id: UUID = UUID.randomUUID(),
    @SerializedName("login") var login: String? = "",
    @SerializedName("password") var password: String = "",
)
