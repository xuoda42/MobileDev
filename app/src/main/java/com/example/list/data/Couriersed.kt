package com.example.list.data

import com.google.gson.annotations.SerializedName

class Couriersed {
    @SerializedName("items") lateinit var items: List<Couriers>
}