package com.harshjoshi.aroundthecorner.apiRequest.domain

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: LaundryApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://ctiot-backend.onrender.com") // Replace with your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LaundryApiService::class.java)
    }
}