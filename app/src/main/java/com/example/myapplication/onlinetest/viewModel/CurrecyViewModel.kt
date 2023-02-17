package com.example.myapplication.onlinetest.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.onlinetest.model.data.CurrencyPair
import com.example.myapplication.onlinetest.model.data.DataModel
import com.example.myapplication.onlinetest.repository.DataRepository
import kotlinx.coroutines.launch
import java.io.IOException

class CurrecyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DataRepository()
    private val dataModel = DataModel()

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val _currencyList: MutableLiveData<List<String>> = MutableLiveData(ArrayList())
    fun getCurrencyList(): LiveData<List<String>> {
        return _currencyList
    }

    private val _allRatesList: MutableLiveData<List<CurrencyPair>> = MutableLiveData(ArrayList())
    fun getAllRatesList(): LiveData<List<CurrencyPair>> {
        return _allRatesList
    }

    init {
        updateCurrencyListFromRepo()
    }


    fun updateSelectedCurrency(targetCurrency: String) {
        dataModel.updateSelectedCurrency(targetCurrency)
        _allRatesList.value = dataModel.currencyResultList
    }

    fun updateAmounted(targetAmount: Double) {
        dataModel.updateAmount(targetAmount)
        _allRatesList.value = dataModel.currencyResultList
    }


    private fun updateCurrencyListFromRepo() {
        viewModelScope.launch {
            try {
                repository.refreshLatestList()
                repository.getLatestList()?.apply {
                    dataModel.createCurrencyData(this)
                    _currencyList.value = dataModel.currencyRateList
                }
                _eventNetworkError.value = false
            } catch (e: IOException) {
                _eventNetworkError.value = true
            }
        }
    }
}