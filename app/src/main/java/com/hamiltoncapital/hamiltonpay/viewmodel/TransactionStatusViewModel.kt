package com.hamiltoncapital.hamiltonpay.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransactionStatusViewModel : ViewModel() {

    fun conversionRateCalculation(
        fromCurrenciesRate: Double,
        toCurrenciesRate: Double,
    ): Double {
        try {
            if (fromCurrenciesRate != 0.0) {
                return String.format("%.2f", (toCurrenciesRate / fromCurrenciesRate))
                    .toDouble()
            }
            return 0.0
        } catch (e: Exception) {
            return 0.0
        }
    }
}