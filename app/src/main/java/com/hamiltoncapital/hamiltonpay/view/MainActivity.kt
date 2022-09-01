package com.hamiltoncapital.hamiltonpay.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.hamiltoncapital.hamiltonpay.R
import com.hamiltoncapital.hamiltonpay.databinding.ActivityMainBinding
import com.hamiltoncapital.hamiltonpay.network.ResponseModel
import com.hamiltoncapital.hamiltonpay.viewmodel.CurrenciesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: CurrenciesViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var currenciesMap: MutableMap<String, Any>
    private lateinit var spinner: SpinnerBottomSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickListener()
        getLatestCurrencies()
    }

    private fun clickListener() {
        binding.txtFromCurrency.setOnClickListener {
            spinner = SpinnerBottomSheet.newInstance(currencies = currenciesMap) {
                Toast.makeText(this, "Clicked on item", Toast.LENGTH_LONG).show()
                binding.txtFromCurrency.text = it.currencyName
                spinner.dismiss()
            }
            spinner.show(supportFragmentManager, "spinner")
        }

        binding.txtToCurrency.setOnClickListener {
            spinner = SpinnerBottomSheet.newInstance(currencies = currenciesMap) {
                Toast.makeText(this, "Clicked on item", Toast.LENGTH_LONG).show()
                binding.txtToCurrency.text = it.currencyName
                spinner.dismiss()
            }
            spinner.show(supportFragmentManager, "spinner")
        }
    }

    private fun getLatestCurrencies() {
        lifecycleScope.launch {
            viewModel.latestCurrencyConversion.collectLatest {
                when (it) {
                    is ResponseModel.Error -> {
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is ResponseModel.Idle -> {
                        Toast.makeText(this@MainActivity, "Idle State", Toast.LENGTH_SHORT)
                            .show()
                    }
                    is ResponseModel.Loading -> {
                        Toast.makeText(this@MainActivity, "Loading...", Toast.LENGTH_SHORT)
                            .show()
                    }
                    is ResponseModel.Success -> {
                        if (it.data?.body()?.conversionRates?.isJsonNull == true) {
                            Toast.makeText(
                                this@MainActivity,
                                "No data found.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val conversionRate = it.data?.body()?.conversionRates;
                            currenciesMap = mutableMapOf<String, Any>().apply {
                                conversionRate?.keySet()
                                    ?.forEach { it1 -> put(it1, conversionRate[it1]) }
                            }
                        }
                    }
                }
            }
        }

        viewModel.viewModelScope.launch {
            viewModel.getLatestCurrencies()
        }
    }
}