package com.smart.id.weatherforcast.network

import WeatherApiResponse
import com.smart.id.weatherforcast.network.data.GetConfirmedRideResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("weather?")
    suspend fun getWeatherResponse(
        @Query("lat") latitude:String,
        @Query("lon") longitude:String,
        @Query("appid") appid:String,
    ): Response<WeatherApiResponse>
}