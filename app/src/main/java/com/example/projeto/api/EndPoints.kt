package com.example.projeto.api

import retrofit2.Call
import retrofit2.http.*


interface EndPoints {

    @GET("/users/")         // Anotaçao necessaria a nivel de retrofit para saber que está a ser feito um pedido GET
    fun getUsers(): Call<List<User>>

    @GET("/users/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @FormUrlEncoded
    @POST("/posts")
    fun postTest(@Field("title") first: String?): Call<OutputPost>
}