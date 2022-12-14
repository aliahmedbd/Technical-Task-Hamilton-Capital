package com.hamiltoncapital.hamiltonpay.view

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hamiltoncapital.hamiltonpay.databinding.ActivityCalculateAmountBinding
import com.hamiltoncapital.hamiltonpay.viewmodel.CalculateAmountViewModel
import java.io.InputStream
import kotlin.properties.Delegates

class CalculateAmountActivity : AppCompatActivity() {

    private lateinit var fromCurrency: String
    private lateinit var toCurrency: String
    private var amount: Double = 0.0
    private var convertedAmount: Double = 0.0

    private lateinit var binding: ActivityCalculateAmountBinding
    private lateinit var viewModel: CalculateAmountViewModel
    private var timer by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateAmountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
    }

    private fun initialize() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        assets.open("config.json")
        fromCurrency = intent.getStringExtra(FROM_CURRENCY).toString()
        toCurrency = intent.getStringExtra(TO_CURRENCY).toString()
        amount = intent.getDoubleExtra(AMOUNT, 0.0)
        convertedAmount = intent.getDoubleExtra(CONVERTED_AMOUNT, 0.0)

        binding.txtFromCurrencies.text = "$amount ${fromCurrency.uppercase()}"
        binding.txtToCurrencies.text = "$convertedAmount ${toCurrency.uppercase()}"
        viewModel = ViewModelProvider(this)[CalculateAmountViewModel::class.java]
        viewModel.getElapsedTime()?.observe(this) {
            it?.let {
                timer = it
                if (it < 0) {
                    showTimeUpDialog()
                    viewModel.cancelTimer()
                } else {
                    binding.txtTimer.text = "$it sec left"
                }
            }
        }

        binding.btnConvert.setOnClickListener {
            showApprovalDialog()
        }
    }

    private fun showTimeUpDialog() {
        val alert: AlertDialog.Builder =
            AlertDialog.Builder(this)
        alert.setMessage("Your transaction approval time up")
        alert.setTitle("Time Up!")
        alert.setPositiveButton(
            "Ok"
        ) { dialog, whichButton ->
            finish()
        }
        alert.show()
    }

    private fun showApprovalDialog() {
        val alert: AlertDialog.Builder =
            AlertDialog.Builder(this)
        alert.setMessage("You are about to get $amount ${fromCurrency.uppercase()} for $convertedAmount ${toCurrency.uppercase()}. Do you approve this transaction?")
        alert.setTitle("Approval Required")
        alert.setPositiveButton(
            "Approve"
        ) { dialog, whichButton ->
            if (timer > 0)
                callTransactionActivity()
            else {
                Toast.makeText(this, "Transaction approval time up!", Toast.LENGTH_LONG).show()
            }
        }
        alert.setNegativeButton("Cancel") { dialog, whichButton ->
            dialog.cancel()
        }
        alert.show()
    }

    private fun callTransactionActivity() {
        val intent = Intent(this, TransactionStatusActivity::class.java)
        intent.putExtra(FROM_CURRENCY, fromCurrency)
        intent.putExtra(TO_CURRENCY, toCurrency)
        intent.putExtra(AMOUNT, amount)
        intent.putExtra(CONVERTED_AMOUNT, convertedAmount)
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}