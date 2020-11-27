package com.example.projeto.api

data class User(            // é necessario criar estas classes para guardar o valor da base de dados que vem do WS
        val id: Int,
        val name: String,
        val email: String,
        val address: Address
)
data class Address(
        val city: String,
        val zipcode: String
)