package com.hamiltoncapital.hamiltonpay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamiltoncapital.hamiltonpay.model.BaseResponse
import com.hamiltoncapital.hamiltonpay.network.ResponseModel
import com.hamiltoncapital.hamiltonpay.repository.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class CurrenciesViewModel(private var dataRepository: DataRepository) : ViewModel() {
    val latestCurrencyConversion = MutableStateFlow<ResponseModel<Response<BaseResponse>>>(ResponseModel.Idle("Idle state"))

    suspend fun getLatestCurrencies() {
        latestCurrencyConversion.emit(ResponseModel.Loading())
        dataRepository.getNewsFromNetwork()?.collect {
            viewModelScope.launch {
                if (it.isSuccessful)
                    latestCurrencyConversion.emit(ResponseModel.Success(it))
                else
                    latestCurrencyConversion.emit(ResponseModel.Error(it.message()))
            }
        }
    }
}