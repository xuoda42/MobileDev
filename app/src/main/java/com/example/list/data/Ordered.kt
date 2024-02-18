package com.example.list.data

import com.google.gson.annotations.SerializedName

class Ordered {
    @SerializedName("items") lateinit var items: List<Orders>
}