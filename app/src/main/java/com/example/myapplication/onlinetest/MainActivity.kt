package com.example.myapplication.onlinetest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.onlinetest.databinding.ActivityMainBinding
import com.example.myapplication.onlinetest.view.ListViewAdapter
import com.example.myapplication.onlinetest.viewModel.CurrecyViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var currecyViewModel: CurrecyViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        OnlineTestApp.applicationContext = applicationContext
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViewModelEvent()
        initListener()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initViewModelEvent() {
        currecyViewModel = ViewModelProvider(this).get(CurrecyViewModel::class.java)
        currecyViewModel.getCurrencyList().observe(this) { currencyList ->

            val dataAdapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                currencyList
            )
            binding.spinner.adapter = dataAdapter
        }

        //ListView
        val listViewAdapter = ListViewAdapter()
        binding.listView.adapter = listViewAdapter
        currecyViewModel.getAllRatesList().observe(this) { allRatesList ->
            listViewAdapter.submitListData(allRatesList)
        }
    }

    private fun initListener() {
        binding.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                p1: View?,
                position: Int,
                p3: Long
            ) {
                val item = parent?.getItemAtPosition(position)
                (item as String?)?.apply {
                    currecyViewModel.updateSelectedCurrency(this)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        binding.plainTextInput.transformationMethod = null
        binding.plainTextInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) return
                currecyViewModel.updateAmounted(p0.toString().toDouble())

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }
}