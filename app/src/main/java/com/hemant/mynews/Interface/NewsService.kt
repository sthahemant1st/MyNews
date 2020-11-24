package com.hemant.mynews.Interface

import com.hemant.mynews.Model.News
import com.hemant.mynews.Model.Website
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {

    @get:GET("v2/sources?apiKey=14adc954de9c4e9a8e04de15532c5bbc")

    val sources : Call<Website>

    @GET
    fun getNewsFormSource(@Url url: String):Call<News>
}