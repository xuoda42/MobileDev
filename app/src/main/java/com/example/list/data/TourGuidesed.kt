package com.example.list.data

import com.google.gson.annotations.SerializedName

class TourGuidesed {
    @SerializedName("items") lateinit var items: List<TourGuides>
}