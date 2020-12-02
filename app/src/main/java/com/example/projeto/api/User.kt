package com.example.projeto.api


data class Problem(
        val id: Int,
        val descr: String,
        val lat: String,
        val lng: String,
        val user_id: Int
)
