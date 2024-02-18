package com.example.list.data

import com.google.gson.annotations.SerializedName

class Companies {
    @SerializedName("items") lateinit var items: List<Company>
}