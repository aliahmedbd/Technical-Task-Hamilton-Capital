package com.hamiltoncapital.hamiltonpay.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.hamiltoncapital.hamiltonpay.databinding.ActivityMainBinding
import com.hamiltoncapital.hamiltonpay.network.ResponseModel
import com.hamiltoncapital.hamiltonpay.viewmodel.CurrenciesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

const val FROM_CURRENCY = "FROM_CURRENCY"
const val TO_CURRENCY = "TO_CURRENCY"
const val AMOUNT = "AMOUNT"
const val CONVERTED_AMOUNT = "CONVERTED_AMOUNT"

class MainActivity : AppCompatActivity() {

    private val viewModel: CurrenciesViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var currenciesMap: MutableMap<String, Any>
    private lateinit var spinner: SpinnerBottomSheet
    private var fromCurrenciesRate: Double = 0.0
    private var toCurrenciesRate: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        clickListener()
        getLatestCurrencies()
    }

    private fun clickListener() {
        binding.txtFromCurrency.setOnClickListener {

            spinner = SpinnerBottomSheet.newInstance(currencies = currenciesMap) {
                binding.txtFromCurrency.text = it.currencyName
                fromCurrenciesRate = it.value
                spinner.dismiss()
            }
            spinner.show(supportFragmentManager, "spinner")
        }

        binding.txtToCurrency.setOnClickListener {
            spinner = SpinnerBottomSheet.newInstance(currencies = currenciesMap) {
                binding.txtToCurrency.text = it.currencyName
                toCurrenciesRate = it.value
                spinner.dismiss()
            }
            spinner.show(supportFragmentManager, "spinner")
        }

        binding.btnCalculate.setOnClickListener {
            if (fromCurrenciesRate == 0.0 || toCurrenciesRate == 0.0) {
                Toast.makeText(this, "Please select currencies!", Toast.LENGTH_LONG).show()
            } else if (binding.edtAmount.text.toString().isNotEmpty()) {
                val fromCurrency = binding.txtFromCurrency.text.toString()
                val toCurrency = binding.txtToCurrency.text.toString()
                val amount = binding.edtAmount.text.toString().toDouble()
                val convertedAmount = viewModel.calculateConvertedCurrencies(
                    fromCurrenciesRate,
                    toCurrenciesRate,
                    amount
                )
                val intent = Intent(this, CalculateAmountActivity::class.java)
                intent.putExtra(FROM_CURRENCY, fromCurrency)
                intent.putExtra(TO_CURRENCY, toCurrency)
                intent.putExtra(AMOUNT, amount)
                intent.putExtra(CONVERTED_AMOUNT, convertedAmount)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid Amount!", Toast.LENGTH_LONG).show()
            }
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
                        Toast.makeText(this@MainActivity, "API call : Idle State", Toast.LENGTH_SHORT)
                            .show()
                    }
                    is ResponseModel.Loading -> {
                        Toast.makeText(this@MainActivity, "API call : Loading...", Toast.LENGTH_SHORT)
                            .show()
                    }
                    is ResponseModel.Success -> {
                        Toast.makeText(this@MainActivity, "API call : Success!", Toast.LENGTH_SHORT)
                            .show()
                        if (it.data?.body()?.conversionRates?.isJsonNull == true) {
                            Toast.makeText(
                                this@MainActivity,
                                "No data found.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val conversionRate = it.data?.body()?.conversionRates
                            currenciesMap = mutableMapOf<String, Any>().apply {
                                conversionRate?.keySet()
                                    ?.forEach { it1 -> put(it1, conversionRate[it1]) }
                            }
                            currenciesMap.entries.removeIf { currenciesMap.size > viewModel.getConfigCurrencySize() }
                        }
                    }
                }
            }
        }

        viewModel.viewModelScope.launch {
            viewModel.getLatestCurrencies()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}