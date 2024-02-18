package com.example.list.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.sql.Time
import java.util.Date

@Entity(tableName = "orders",
    indices = [Index("id"), Index("courier_id", "id")],
    foreignKeys = [
        ForeignKey(
            entity = Couriers::class,
            parentColumns = ["id"],
            childColumns = ["courier_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Orders(
    @SerializedName("id") @PrimaryKey var id : Int=0,
    @SerializedName("address") var address: String="",
    @SerializedName("orderDetails") @ColumnInfo(name = "order_details") var orderDetails: String = "",
    @SerializedName("time") var time : String="",
    @SerializedName("date") @ColumnInfo(name = "date") var date : Date = Date(),
    @SerializedName("courierID") @ColumnInfo(name = "courier_id") var courierID: Int=0,
    @SerializedName("phone") var phone: String="",
    @SerializedName("timeTravel") @ColumnInfo(name = "time_travel") var timeTravel: Int=0,
    @SerializedName("price") var price: String="",
    @SerializedName("comment") var comment: String=""
)
