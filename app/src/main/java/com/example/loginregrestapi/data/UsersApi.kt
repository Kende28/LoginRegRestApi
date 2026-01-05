package com.example.loginregrestapi.data

import retrofit2.http.GET

interface UsersApi {
    @GET("/o1RCDT/users")
    suspend fun getUsers(): List<UserDto>
}