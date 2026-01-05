package com.example.loginregrestapi.data

import com.squareup.moshi.Json

data class UserDto(
    val id: Long,
    @Json(name = "Name") val name: String,
    @Json(name = "Email") val email: String,
    @Json(name = "Password") val password: String,
    @Json(name = "Birthdate") val birthDate: String
)