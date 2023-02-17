package com.example.myapplication.onlinetest.model.data

import android.util.Log
import kotlin.math.roundToInt

class DataModel {
    private var _currencyRateMap = HashMap<String, Double>()
    private var _currencyRateList:List<String> = kotlin.collections.ArrayList()
    private var _currencyResultList: List<CurrencyPair> = kotlin.collections.ArrayList()

    val currencyResultList: List<CurrencyPair>
        get() = _currencyResultList

    val currencyRateList: List<String>
        get() = _currencyRateList
    private var _selectedCurrency: String = "USD"
    private var _selectedAmount:Double = 0.0

    fun createCurrencyData(rateMap: HashMap<String, Double>) {
        _currencyRateMap = rateMap
        _currencyRateList = rateMap.keys.toList().sorted()
    }

    fun updateSelectedCurrency(target: String) {
        if(_currencyRateMap.contains(target)) {
            _selectedCurrency = target
            generateExchangeRate()
        } else {
            Log.w(Log.WARN.toString(), "No such currency $target")
        }
    }

    fun updateAmount(target: Double) {
        _selectedAmount = target
        generateExchangeRate()
    }


    private fun generateExchangeRate() {
        val baseInUSD = _selectedAmount / _currencyRateMap[_selectedCurrency]!!
        val resultList = kotlin.collections.ArrayList<CurrencyPair>()
        _currencyRateList.forEach {currency ->
            val number = baseInUSD * _currencyRateMap[currency]!!
            resultList.add(CurrencyPair(currency, (number * 100000.0).roundToInt() / 100000.0))
        }
        _currencyResultList = resultList
    }
}