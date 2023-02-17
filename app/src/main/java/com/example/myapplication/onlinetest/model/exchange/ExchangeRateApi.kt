package com.example.myapplication.onlinetest.model.exchange

import com.example.myapplication.onlinetest.model.exchange.RetrofitClient.APP_ID_PARAM
import retrofit2.http.GET

interface ExchangeRateApi {
    @GET("api/latest.json?${APP_ID_PARAM}")
    suspend fun getLastList(): ExchangeRate?
}


