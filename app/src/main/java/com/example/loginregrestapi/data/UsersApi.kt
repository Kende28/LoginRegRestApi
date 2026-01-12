package com.example.loginregrestapi.data

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsersApi {
    @GET("/o1RCDT/users")
    suspend fun getUsers(): List<UserDto>

    @GET("/o1RCDT/users/{id}")
    suspend fun getUser(@Path("id") id: Long): UserDto

    @PUT("/o1RCDT/users/{id}")
    suspend fun updateUser(@Path("id") id: Long, @Body req: UpdateUserRequest): UserDto

    @DELETE("/o1RCDT/users/{id}")
    suspend fun deleteUser(@Path("id") id: Long)


}
