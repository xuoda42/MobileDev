package com.example.list.api

import com.example.list.data.Company
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class PostCompany (
    @SerializedName("action") val action:Int,
    @SerializedName ("company") val company: Company
)