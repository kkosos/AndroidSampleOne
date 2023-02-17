package com.example.myapplication.onlinetest.model.exchange

import com.google.gson.Gson

class ExchangeRate(
    val disclaimer: String?,
    val license: String?,
    val timestamp: Long?,
    val base: String?,
    val rates: Map<String, Double>?
) {
    fun ratesToJson():String? {
        return rates?.run {
            return@run Gson().toJson(this)
        }
    }
}

