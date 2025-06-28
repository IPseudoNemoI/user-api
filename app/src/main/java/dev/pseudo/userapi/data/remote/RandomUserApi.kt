package dev.pseudo.userapi.data.remote

import dev.pseudo.userapi.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {
    @GET("api/")
    suspend fun getUsers(
        @Query("results") count: Int = 20
    ): UserResponse
}