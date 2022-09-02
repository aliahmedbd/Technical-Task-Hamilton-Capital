package com.hamiltoncapital.hamiltonpay.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hamiltoncapital.hamiltonpay.databinding.ActivityTransactionStatusBinding
import com.hamiltoncapital.hamiltonpay.viewmodel.TransactionStatusViewModel

class TransactionStatusActivity : AppCompatActivity() {

    private var convertedAmount: Double = 0.0
    private var amount: Double = 0.0
    private lateinit var toCurrency: String
    private lateinit var fromCurrency: String
    private lateinit var binding: ActivityTransactionStatusBinding
    private lateinit var viewModel: TransactionStatusViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
    }

    private fun initialize() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        viewModel = ViewModelProvider(this)[TransactionStatusViewModel::class.java]
        fromCurrency = intent.getStringExtra(FROM_CURRENCY).toString()
        toCurrency = intent.getStringExtra(TO_CURRENCY).toString()
        amount = intent.getDoubleExtra(AMOUNT, 0.0)
        convertedAmount = intent.getDoubleExtra(CONVERTED_AMOUNT, 0.0)

        binding.txtSuccessMessage.text =
            "Great now you have $convertedAmount $toCurrency in your account"

        binding.txtConversionRate.text = "Your conversion rate was 1/${
            viewModel.conversionRateCalculation(
                amount,
                convertedAmount
            )
        }"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}