package com.example.list.api

import com.example.list.data.Orders
import com.google.gson.annotations.SerializedName

class PostOrders (
    @SerializedName("action") val action:Int,
    @SerializedName("orders") val orders: Orders
)