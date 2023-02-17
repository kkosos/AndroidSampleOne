package com.example.myapplication.onlinetest.model.exchange

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASEURL = "https://openexchangerates.org/"
    private const val APP_KEY = "92ad6ab2a8634914be02fd129c804b43"
    const val APP_ID_PARAM = "app_id=$APP_KEY"
    private val client: Retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    val latestApi: ExchangeRateApi = client.create(ExchangeRateApi::class.java)

}