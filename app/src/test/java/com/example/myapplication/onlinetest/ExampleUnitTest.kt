package com.example.myapplication.onlinetest


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myapplication.onlinetest.model.data.DataModel
import com.example.myapplication.onlinetest.model.exchange.ExchangeRate
import com.example.myapplication.onlinetest.model.exchange.RetrofitClient
import com.example.myapplication.onlinetest.model.localStoarge.SharedPref
import com.example.myapplication.onlinetest.repository.DataRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getListWork() = runTest {
        val data = fetchExchangeRateList()
        if (data == null) {
            error("Empty data")
        } else {
            data.rates?.forEach { entry -> println("${entry.key} ${entry.value}") }
        }
    }

    private suspend fun fetchExchangeRateList(): ExchangeRate? {
        return try {
            RetrofitClient.latestApi.getLastList()
        } catch (e: Exception) {
            null
        }
    }

    @Test
    fun testDataModel() {
        val dataModel = DataModel()
        val testMap = HashMap<String, Double>()
        testMap["USD"] = 1.0
        testMap["JPY"] = 134.0
        testMap["ABE"] = 5.0
        dataModel.createCurrencyData(testMap)
        var amount = 5.0
        dataModel.updateAmount(amount)
        dataModel.updateSelectedCurrency("USD")

        dataModel.currencyResultList.forEach {
            when(it.currency) {
                "USD" -> assertEquals(1.0 * amount, it.dollar,1.0)
                "JPY" -> assertEquals(134.0 * amount, it.dollar,1.0)
                "ABE" -> assertEquals(5.0 * amount, it.dollar,1.0)
            }
        }

        amount = 100.0
        dataModel.updateAmount(amount)
        dataModel.updateSelectedCurrency("JPY")

        dataModel.currencyResultList.forEach {
            when(it.currency) {
                "USD" -> assertEquals(amount / 134.0 * 1.0 , it.dollar,1.0)
                "JPY" -> assertEquals(amount / 134.0 * 134.0, it.dollar,1.0)
                "ABE" -> assertEquals(amount / 134.0 * 5.0 , it.dollar,1.0)
            }
        }

    }





}