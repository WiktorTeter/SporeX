package com.example.sporex_app.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // IMPORTANT:
    // - If you use the Android Emulator and Flask runs on your PC at port 5000:
    //       use  http://10.0.2.2:5000/
    // - If you use a real phone on the same Wi-Fi:
    //       use  http://YOUR_PC_LOCAL_IP:5000/


    // TODO: replace with production URL when deployed

    private const val BASE_URL = "http://10.39.52.156:5000/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val api: SporexApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SporexApi::class.java)
    }
}
