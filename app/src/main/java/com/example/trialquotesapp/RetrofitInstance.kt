package com.example.trialquotesapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val base_url = "https://zenquotes.io/api/"
    private fun getInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val quoteApi:QuoteApi = getInstance().create(QuoteApi::class.java)
}