package com.example.list.api

import com.example.list.data.TourGuides
import com.google.gson.annotations.SerializedName

class PostTourGuides (
    @SerializedName("action") val action: Int,
    @SerializedName("tourGuide") val tourGuides: TourGuides
)