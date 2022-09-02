package com.hamiltoncapital.hamiltonpay.di

import android.content.Context
import com.hamiltoncapital.hamiltonpay.network.APIClient
import com.hamiltoncapital.hamiltonpay.network.APIService
import com.hamiltoncapital.hamiltonpay.repository.DataRepository
import com.hamiltoncapital.hamiltonpay.viewmodel.CalculateAmountViewModel
import com.hamiltoncapital.hamiltonpay.viewmodel.CurrenciesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named("data_repo")) { DataRepository(NetworkModule(androidContext())) }
    viewModel { CurrenciesViewModel(get(named("data_repo"))) }
}

class NetworkModule(val context: Context) {
    fun sourceOfNetwork(): APIService {
        return APIClient.create()
    }
}