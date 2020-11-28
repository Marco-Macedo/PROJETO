package com.example.projeto.api

import retrofit2.Call
import retrofit2.http.*


interface EndPoints {

    @FormUrlEncoded
    @POST("/myslim/api/user")
    fun postLogin(
            @Field("name") name: String?,
            @Field("password") password: String?): Call<OutputPost>
}