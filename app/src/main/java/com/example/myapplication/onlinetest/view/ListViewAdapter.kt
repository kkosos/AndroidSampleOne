package com.example.myapplication.onlinetest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.onlinetest.R
import com.example.myapplication.onlinetest.model.data.CurrencyPair

class ListViewAdapter(): RecyclerView.Adapter<ListViewAdapter.ViewHolder>() {
    private var _mapList :List<CurrencyPair> = ArrayList()

    fun submitListData(data: List<CurrencyPair>) {
        _mapList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.currencyText.text = _mapList[position].currency
        viewHolder.dollarText.text = _mapList[position].dollar.toString()
    }
    override fun getItemCount() = _mapList.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val currencyText: TextView
        val dollarText: TextView

        init {
            // Define click listener for the ViewHolder's View
            currencyText = view.findViewById(R.id.currency)
            dollarText = view.findViewById(R.id.dollar)
        }
    }

}