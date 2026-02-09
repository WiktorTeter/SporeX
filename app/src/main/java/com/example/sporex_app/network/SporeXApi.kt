package com.example.sporex_app.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path

interface SporexApi {

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>


    @GET("products")
    suspend fun getProducts(): Response<List<ProductSummary>>

    @GET("products/{id}")
    suspend fun getProductDetail(@Path("id") id: String): Response<ProductDetail>

}
