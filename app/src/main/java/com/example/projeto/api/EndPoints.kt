package com.example.projeto.api

import retrofit2.Call
import retrofit2.http.*


interface EndPoints {
    @GET("/myslim/api/problem")
    fun getProblem(): Call<List<Problem>>

    @FormUrlEncoded
    @POST("/myslim/api/user")
    fun postLogin(
            @Field("name") name: String?,
            @Field("password") password: String?): Call<OutputPost>
}