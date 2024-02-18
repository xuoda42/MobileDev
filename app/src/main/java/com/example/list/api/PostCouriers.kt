package com.example.list.api

import com.example.list.data.Couriers
import com.google.gson.annotations.SerializedName

class PostCouriers (
    @SerializedName("action") val action: Int,
    @SerializedName("courier") val couriers: Couriers
)