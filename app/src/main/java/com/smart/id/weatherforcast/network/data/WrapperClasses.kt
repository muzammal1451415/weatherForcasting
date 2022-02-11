package com.smart.id.weatherforcast.network.data

import com.google.gson.annotations.SerializedName

data class GetConfirmedRideResponse (

    @SerializedName("message") val message:String,
    @SerializedName("data") val data:String
)