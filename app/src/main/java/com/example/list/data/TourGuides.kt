package com.example.list.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tourGuides",
    indices = [Index("id"), Index("company_id")],
    foreignKeys = [
        ForeignKey(
            entity = Company::class,
            parentColumns = ["id"],
            childColumns = ["company_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TourGuides(
    @SerializedName("id") @PrimaryKey val id: Int=0,
    @SerializedName("name") @ColumnInfo(name = "tour_guide_name")var name: String="",
    @SerializedName("companyID") @ColumnInfo(name = "company_id")var companyID: Int = 0
)
