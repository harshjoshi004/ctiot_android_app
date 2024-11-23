package com.harshjoshi.aroundthecorner.apiRequest.data

import com.google.gson.annotations.SerializedName

data class ResponseMessage(
    @SerializedName("message") val message: String
)