package com.hamiltoncapital.hamiltonpay.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hamiltoncapital.hamiltonpay.R

class CurrencyListAdapter(
    private val currenciesMap: MutableMap<String, Any>,
    val onItemClick: ((Int) -> Unit)
) : RecyclerView.Adapter<CurrencyListAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCurrencies = itemView.findViewById(R.id.textView) as TextView
        fun bindItems(value: String) {
            txtCurrencies.text = value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(currenciesMap.keys.elementAt(position))
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return currenciesMap.size
    }
}