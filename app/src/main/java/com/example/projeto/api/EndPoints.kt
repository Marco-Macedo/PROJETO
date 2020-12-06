package com.example.projeto.api

import retrofit2.Call
import retrofit2.http.*


interface EndPoints {
    @GET("/myslim/api/problem")
    fun getProblem(): Call<List<problemas>>

    @FormUrlEncoded
    @POST("/myslim/api/user")
    fun postLogin(
            @Field("name") name: String?,
            @Field("password") password: String?): Call<OutputPost>

    @FormUrlEncoded
    @POST("/myslim/api/registarproblema")
    fun postRegister(
        @Field("descr") descr: String?,
        @Field("latitude") latitude: String?,
        @Field("longitude") longitude: String?,
        @Field("user_id") user_id: Int
    ): Call<OutputPost>

    @GET("myslim/api/problema/{id}")
    fun deleteProblema(): Call<OutputPost>

}