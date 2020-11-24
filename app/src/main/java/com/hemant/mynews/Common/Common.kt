package com.hemant.mynews.Common

import com.hemant.mynews.Interface.NewsService
import com.hemant.mynews.Remote.RetrofitClient
import retrofit2.create
import java.lang.StringBuilder

object Common {
    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "14adc954de9c4e9a8e04de15532c5bbc"

    //implementing NewsService interface

    val newsService : NewsService
        get() = RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    fun getNewsAPI(source : String):String {
        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()
        return apiUrl
    }

}