package com.example.myapplication.onlinetest.repository

import com.example.myapplication.onlinetest.OnlineTestApp
import com.example.myapplication.onlinetest.model.exchange.RetrofitClient
import com.example.myapplication.onlinetest.model.localStoarge.SharedPref
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type


class DataRepository {
    companion object {
        private const val CURRENCY_RATE = "CurrencyRate"
    }

    private fun needFetchNewData():Boolean {
        return SharedPref.isOver30Min(OnlineTestApp.applicationContext) || getLatestList() == null
    }

    suspend fun refreshLatestList() {
        if (!needFetchNewData()) return
        withContext(Dispatchers.IO) {
            try {
                val exchangeList = RetrofitClient.latestApi.getLastList()
                exchangeList?.apply {
                    ratesToJson()?.let { json ->
                        SharedPref.put(
                            OnlineTestApp.applicationContext,
                            Pair<String, Any>(CURRENCY_RATE, json)
                        )
                        SharedPref.saveCurrentTime(OnlineTestApp.applicationContext)
                    }
                }
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun getLatestList(): HashMap<String, Double>? {
        return SharedPref.getJsonString(OnlineTestApp.applicationContext, CURRENCY_RATE)?.run {
            val gson = Gson()
            val type: Type = object : TypeToken<HashMap<String, Double>?>() {}.type
            return@run gson.fromJson<HashMap<String, Double>>(this, type)
        }
    }
}