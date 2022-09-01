package com.hamiltoncapital.hamiltonpay.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hamiltoncapital.hamiltonpay.R
import com.hamiltoncapital.hamiltonpay.adapter.CurrencyListAdapter
import com.hamiltoncapital.hamiltonpay.model.CurrenciesRate

class SpinnerBottomSheet : BottomSheetDialogFragment() {

    private lateinit var onClicked: (CurrenciesRate) -> Unit
    private lateinit var currencies: MutableMap<String, Any>
    private lateinit var rvCurrencyList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.bottom_sheet_spinner, container, false)
        rvCurrencyList = view.findViewById(R.id.rvCurrencies)
        setCurrenciesList();
        return view
    }

    private fun setCurrenciesList() {
        rvCurrencyList.layoutManager = LinearLayoutManager(context)
        val adapter: CurrencyListAdapter = CurrencyListAdapter(currencies) {
            val currenciesRate = CurrenciesRate(currencies.keys.elementAt(it), currencies.values.elementAt(it).toString().toDouble(), it)
            onClicked.invoke(currenciesRate)
        }
        rvCurrencyList.adapter = adapter
    }

    companion object {
        fun newInstance(currencies: MutableMap<String, Any>, callBack: (CurrenciesRate) -> Unit) =
            SpinnerBottomSheet().apply {
                this.currencies = currencies
                this.onClicked = callBack
            }
    }
}