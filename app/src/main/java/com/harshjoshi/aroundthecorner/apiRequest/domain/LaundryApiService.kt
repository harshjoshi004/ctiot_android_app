package com.harshjoshi.aroundthecorner.apiRequest.domain

import com.harshjoshi.aroundthecorner.apiRequest.data.Person
import com.harshjoshi.aroundthecorner.apiRequest.data.ResponseMessage
import retrofit2.http.Body
import retrofit2.http.POST

interface LaundryApiService {
    @POST("laundry")
    suspend fun laundryAd(@Body user: Person): ResponseMessage

    @POST("babajuice")
    suspend fun babaJuiceAd(@Body user: Person): ResponseMessage
}