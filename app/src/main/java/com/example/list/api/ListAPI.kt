package com.example.list.api

import retrofit2.http.GET
import com.example.list.data.Companies
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import com.example.list.data.TourGuidesed
import com.example.list.data.Ordered
import com.example.list.data.User
import com.example.list.data.UserLogin
import retrofit2.Call
import retrofit2.http.Query

const val APPEND_COMPANY = 11
const val UPDATE_COMPANY = 12
const val DELETE_COMPANY = 13
const val APPEND_MAKERS = 21
const val UPDATE_MAKERS = 22
const val DELETE_MAKERS = 23
const val APPEND_DOCUMENTS = 31
const val UPDATE_DOCUMENTS = 32
const val DELETE_DOCUMENTS = 33

interface ListAPI{
    @GET("code=10")
    fun getCompanys(): Call<Companies>

    @Headers("Content-Type: application/json")
    @POST("company")
    fun postCompany(@Body postCompany: PostCompany): Call<PostResult>

    @GET("code=20")
    fun getTourGuides(): Call<TourGuidesed>

    @Headers("Content-Type: application/json")
    @POST("tourGuides")
    fun postTourGuide(@Body postTourGuides: PostTourGuides): Call<PostResult>

    @GET("code=30")
    fun getOrders(): Call<Ordered>

    @Headers("Content-Type: application/json")
    @POST("orders")
    fun postOrders(@Body postOrders: PostOrders): Call<PostResult>

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    fun login(@Body user: UserLogin): Call<PostResult>

    @Headers("Content-Type: application/json")
    @POST("auth/registration")
    fun registration(@Body user: User): Call<PostResult>

}