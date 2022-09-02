package com.hamiltoncapital.hamiltonpay

import com.hamiltoncapital.hamiltonpay.viewmodel.CurrenciesViewModel
import com.hamiltoncapital.hamiltonpay.viewmodel.TransactionStatusViewModel
import org.junit.Assert
import org.junit.Test

class CurrencyConverterTest {
    private val DELTA = 1e-15
    private var fromCurrencyValue: Double = 0.0
    private var toCurrencyValue: Double = 0.0

    @Test
    fun validateConverter() {
        fromCurrencyValue = 1.0
        toCurrencyValue = 2.0
        val amount = 100.0
        val result = CurrenciesViewModel(null).calculateConvertedCurrencies(
            fromCurrencyValue,
            toCurrencyValue,
            amount
        )
        Assert.assertEquals(200.0, result, DELTA)
    }

    @Test
    fun validateConverterInvalidCase() {
        fromCurrencyValue = 0.0
        toCurrencyValue = 5.0
        val amount = 100.0
        val result = CurrenciesViewModel(null).calculateConvertedCurrencies(
            fromCurrencyValue,
            toCurrencyValue,
            amount
        )
        Assert.assertEquals(0.0, result, DELTA)
    }

    @Test
    fun validateRatio() {
        fromCurrencyValue = 10.0
        toCurrencyValue = 1.0
        val result = TransactionStatusViewModel().conversionRateCalculation(
            fromCurrencyValue,
            toCurrencyValue,
        )
        Assert.assertEquals(0.1, result, DELTA)
    }

}