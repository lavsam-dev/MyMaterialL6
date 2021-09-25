package com.learn.lavsam.mymaterial.ui.picture

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String, @Query("date") apiDate: String): Call<PODServerResponseData>
}