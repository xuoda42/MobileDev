package com.example.list.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.UUID

@Entity(
    indices = [Index("id")]
)


data class Company(
    @SerializedName("id") @PrimaryKey val id: Int = 0,
    @SerializedName("name") @ColumnInfo(name = "company_name") var name: String = ""
)
