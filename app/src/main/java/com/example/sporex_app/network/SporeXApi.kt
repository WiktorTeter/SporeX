package com.example.sporex_app.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SporexApi {

    @POST("/api/login")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<LoginResponse>
}
